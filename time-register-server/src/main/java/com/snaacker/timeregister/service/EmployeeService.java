package com.snaacker.timeregister.service;

import com.snaacker.timeregister.exception.TimeRegisterBadRequestException;
import com.snaacker.timeregister.exception.TimeRegisterException;
import com.snaacker.timeregister.exception.TimeRegisterObjectNotFoundException;
import com.snaacker.timeregister.model.request.EmployeeRequest;
import com.snaacker.timeregister.model.request.TimeRecordRequest;
import com.snaacker.timeregister.model.response.EmployeeResponse;
import com.snaacker.timeregister.model.response.TimeRegisterGenericResponse;
import com.snaacker.timeregister.model.response.UserMultipleTimeRecordResponse;
import com.snaacker.timeregister.model.response.UserSingleTimeRecordResponse;
import com.snaacker.timeregister.persistent.Employee;
import com.snaacker.timeregister.persistent.EmployeeRestaurant;
import com.snaacker.timeregister.persistent.Message;
import com.snaacker.timeregister.persistent.Restaurant;
import com.snaacker.timeregister.persistent.TimesheetRecord;
import com.snaacker.timeregister.repository.EmployeeRepository;
import com.snaacker.timeregister.repository.MessageRepository;
import com.snaacker.timeregister.repository.RestaurantRepository;
import com.snaacker.timeregister.repository.TimesheetRecordRepository;
import com.snaacker.timeregister.utils.Constants;
import com.snaacker.timeregister.utils.DtoTransformation;
import com.snaacker.timeregister.utils.Utilities;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {
    Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private RestaurantRepository restaurantRepository;
    @Autowired private TimesheetRecordRepository timesheetRecordRepository;

    @Autowired private MessageRepository messageRepository;

    public TimeRegisterGenericResponse<EmployeeResponse> getListEmployee(
            int startingPage, int pageSize) {

        Pageable pageable = PageRequest.of(startingPage, pageSize);
        List<EmployeeResponse> listUser =
                employeeRepository.findAll(pageable).stream()
                        .map(DtoTransformation::user2UserResponse)
                        .collect(Collectors.toList());
        return new TimeRegisterGenericResponse<>(listUser, pageSize, startingPage);
    }

    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest)
            throws TimeRegisterBadRequestException {
        Employee employee = new Employee(employeeRequest);
        if (null != employeeRequest.getRestaurantName()) {
            EmployeeRestaurant employeeRestaurant = new EmployeeRestaurant();
            employeeRestaurant.setEmployee(employee);
            Restaurant returnRestaurant =
                    restaurantRepository.findByName(employeeRequest.getRestaurantName());
            if (null == returnRestaurant) {
                throw new TimeRegisterBadRequestException("Restaurant does not exist");
            }
            employeeRestaurant.setRestaurant(returnRestaurant);
            if (employee.getRoleName().getRoleValue().equals(Constants.MANAGER)) {
                employeeRestaurant.setRestaurantManager(true);
            }
        } else {
            logger.warn("You need to assign user to existed restaurant later");
        }
        try {
            employee.setPassword(Utilities.encrypt(employeeRequest.getPassword()));
        } catch (NoSuchPaddingException
                | NoSuchAlgorithmException
                | InvalidKeyException
                | IllegalBlockSizeException
                | BadPaddingException e) {
            throw new TimeRegisterBadRequestException(e.getMessage());
        }
        Employee createdEmployee = employeeRepository.save(employee);
        return DtoTransformation.user2UserResponse(createdEmployee);
    }

    public EmployeeResponse editUser(Long id, EmployeeRequest employeeRequest)
            throws TimeRegisterBadRequestException {
        Employee employee = employeeRepository.getById(id);
        if (null != employeeRequest.getEmail()) {
            employee.setEmail(employeeRequest.getEmail());
        }
        if (null != employeeRequest.getAddress()) {
            employee.setAddress(employeeRequest.getAddress());
        }
        if (null != employeeRequest.getFirstName()) {
            employee.setFirstName(employeeRequest.getFirstName());
        }
        if (null != employeeRequest.getLastName()) {
            employee.setLastName(employeeRequest.getLastName());
        }
        if (null != employeeRequest.getPhoneNumber()) {
            employee.setPhoneNumber(employeeRequest.getPhoneNumber());
        }
        if (null != employeeRequest.getRoleName()) {
            employee.setRoleName(employeeRequest.getRoleName());
            if (employeeRequest.getRoleName().equals("ADMIN")) employee.isAdmin();
        }
        if (null != employeeRequest.getPassword()) {
            try {
                employee.setPassword(Utilities.encrypt(employeeRequest.getPassword()));
            } catch (NoSuchPaddingException
                    | NoSuchAlgorithmException
                    | InvalidKeyException
                    | IllegalBlockSizeException
                    | BadPaddingException e) {
                throw new TimeRegisterBadRequestException(e.getMessage());
            }
        }
        // TODO: too lazy but must fix - timezone or UTC here
        Employee updatedEmployee = employeeRepository.save(employee);
        return DtoTransformation.user2UserResponse(updatedEmployee);
    }

    public String deleteUser(long id) throws TimeRegisterBadRequestException {
        Employee deleteEmployee =
                employeeRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new TimeRegisterBadRequestException("User does not exist"));
        employeeRepository.delete(deleteEmployee);

        return "OK";
    }

    public EmployeeResponse getUserByName(String username) {
        return DtoTransformation.user2UserResponse(employeeRepository.findByAccountName(username));
    }

    public EmployeeResponse getUserById(Long id) throws TimeRegisterObjectNotFoundException {
        return DtoTransformation.user2UserResponse(
                employeeRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new TimeRegisterObjectNotFoundException(
                                                "User does not exist")));
    }

    @Transactional
    public UserMultipleTimeRecordResponse addTimeRecord(
            Long id, TimeRecordRequest timeRecordRequest) throws TimeRegisterException {
        Employee employee = employeeRepository.findById(id).orElseThrow(TimeRegisterException::new);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        List<TimesheetRecord> listTimesheetRecord =
                timesheetRecordRepository.findRecordByWorkingDateAndUserId(
                        dateFormat.format(timeRecordRequest.getFromTime()), id);
        listTimesheetRecord.stream()
                .forEach(
                        timesheetRecord -> {
                            // TODO: verify the logic
                            if (timeRecordRequest
                                                    .getFromTime()
                                                    .compareTo(timesheetRecord.getFromTime())
                                            > 0
                                    && timeRecordRequest
                                                    .getToTime()
                                                    .compareTo(timesheetRecord.getToTime())
                                            < 0) {
                                throw new TimeRegisterBadRequestException("Invalid timeline");
                            }
                            if (timeRecordRequest
                                                    .getFromTime()
                                                    .compareTo(timesheetRecord.getFromTime())
                                            < 0
                                    && timeRecordRequest
                                                    .getToTime()
                                                    .compareTo(timesheetRecord.getToTime())
                                            < 0
                                    && timeRecordRequest
                                                    .getToTime()
                                                    .compareTo(timesheetRecord.getFromTime())
                                            < 0) {
                                throw new TimeRegisterBadRequestException("Invalid timeline");
                            }
                            if (timeRecordRequest
                                                    .getFromTime()
                                                    .compareTo(timesheetRecord.getFromTime())
                                            > 0
                                    && timeRecordRequest
                                                    .getToTime()
                                                    .compareTo(timesheetRecord.getToTime())
                                            > 0
                                    && timeRecordRequest
                                                    .getFromTime()
                                                    .compareTo(timesheetRecord.getToTime())
                                            > 0) {
                                throw new TimeRegisterBadRequestException("Invalid timeline");
                            }
                        });
        TimesheetRecord timesheetRecord = new TimesheetRecord();
        // TODO: Add logic check working time (working time constrain)
        timesheetRecord.setWorkingDate(dateFormat.format(timeRecordRequest.getFromTime()));
        timesheetRecord.setToTime(timeRecordRequest.getToTime());
        timesheetRecord.setFromTime(timeRecordRequest.getFromTime());
        timesheetRecord.setTimesheetType(timeRecordRequest.getType());
        timesheetRecord.setEmployee(employee);
        timesheetRecordRepository.save(timesheetRecord);

        employee.getEmployeeRestaurants()
                .forEach(
                        userRestaurant -> {
                            long restaurantId = userRestaurant.getRestaurant().getId();
                            Employee restaurantManager =
                                    restaurantRepository.getById(restaurantId).getManager();
                            Message message = new Message();
                            message.setContent(
                                    "User "
                                            + employee.getAccountId()
                                            + " has submitted a timesheet record");
                            message.setEmployee(restaurantManager);
                            messageRepository.save(message);
                        });

        // TODO: return timesheet record of that week
        List<TimesheetRecord> listTimeSheetRecord =
                timesheetRecordRepository.getTimeRecordOnSavedMonthOfUser(employee.getId());

        UserMultipleTimeRecordResponse userMultipleTimeRecordResponse =
                DtoTransformation.Object2UserTimeRecordResponse(listTimeSheetRecord, employee);

        return userMultipleTimeRecordResponse;
    }

    public UserMultipleTimeRecordResponse approvedTimesheet(
            Long userId, List<Long> listTimeSheetId) {
        Employee returnEmployee = employeeRepository.getById(userId);
        listTimeSheetId.stream()
                .forEach(
                        timesheetId -> {
                            TimesheetRecord returnTimesheet =
                                    timesheetRecordRepository.getById(timesheetId);
                            returnTimesheet.isApproved();
                            timesheetRecordRepository.save(returnTimesheet);
                        });

        Message message = new Message();
        // TODO: introduce user display name
        message.setContent("Your timesheet has been approved");
        message.setEmployee(returnEmployee);
        messageRepository.save(message);
        UserMultipleTimeRecordResponse userMultipleTimeRecordResponse =
                new UserMultipleTimeRecordResponse();
        userMultipleTimeRecordResponse.setUser(new EmployeeResponse(returnEmployee));
        // TODO: get timerecord values and set timerecord here
        userMultipleTimeRecordResponse.setTimeRecords(new TimeRegisterGenericResponse<>());
        return userMultipleTimeRecordResponse;
    }

    public UserSingleTimeRecordResponse editTimeRecord(
            Long userId, Long recordId, TimeRecordRequest timeRecord) {
        // check user existence
        Employee returnEmployee = employeeRepository.getById(userId);

        TimesheetRecord requestChangeTimesheet = timesheetRecordRepository.getById(recordId);
        requestChangeTimesheet.setTimesheetType(timeRecord.getType());
        requestChangeTimesheet.setToTime(timeRecord.getToTime());
        requestChangeTimesheet.setFromTime(timeRecord.getFromTime());
        requestChangeTimesheet.setTimesheetType(timeRecord.getType());
        returnEmployee
                .getEmployeeRestaurants()
                .forEach(
                        userRestaurant -> {
                            long restaurantId = userRestaurant.getRestaurant().getId();
                            Employee restaurantManager =
                                    restaurantRepository.getById(restaurantId).getManager();
                            Message message = new Message();
                            // TODO: introduce user display name
                            message.setContent(
                                    "User "
                                            + returnEmployee.getAccountId()
                                            + " has edit his/her timesheet");
                            message.setEmployee(restaurantManager);
                            messageRepository.save(message);
                        });
        TimesheetRecord returnTimesheet = timesheetRecordRepository.save(requestChangeTimesheet);
        return new UserSingleTimeRecordResponse(returnEmployee, returnTimesheet);
    }

    public EmployeeResponse assignRestaurant(Long user_id, Long restaurant_id) {
        Employee employee = employeeRepository.findById(user_id).get();
        Restaurant restaurant = restaurantRepository.findById(restaurant_id).get();
        EmployeeRestaurant employeeRestaurant = new EmployeeRestaurant();
        employeeRestaurant.setEmployee(employee);
        employeeRestaurant.setRestaurant(restaurant);
        employeeRestaurant.setRestaurantManager(
                employee.getRoleName().getRoleValue().equals(Constants.MANAGER));
        Set<EmployeeRestaurant> setEmployeeRestaurant =
                new HashSet<>(Arrays.asList(employeeRestaurant));
        employee.setEmployeeRestaurants(setEmployeeRestaurant);
        Employee returnEmployee = employeeRepository.save(employee);
        return new EmployeeResponse(returnEmployee);
    }
}
