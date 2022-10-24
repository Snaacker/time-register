package com.snaacker.timeregister.service;

import com.snaacker.timeregister.exception.TimeRegisterBadRequestException;
import com.snaacker.timeregister.exception.TimeRegisterException;
import com.snaacker.timeregister.exception.TimeRegisterObjectNotFoundException;
import com.snaacker.timeregister.model.request.TimeRecordRequest;
import com.snaacker.timeregister.model.request.UserRequest;
import com.snaacker.timeregister.model.response.TimeRegisterGenericResponse;
import com.snaacker.timeregister.model.response.UserMultipleTimeRecordResponse;
import com.snaacker.timeregister.model.response.UserResponse;
import com.snaacker.timeregister.model.response.UserSingleTimeRecordResponse;
import com.snaacker.timeregister.persistent.Message;
import com.snaacker.timeregister.persistent.Restaurant;
import com.snaacker.timeregister.persistent.TimesheetRecord;
import com.snaacker.timeregister.persistent.User;
import com.snaacker.timeregister.persistent.UserRestaurant;
import com.snaacker.timeregister.repository.MessageRepository;
import com.snaacker.timeregister.repository.RestaurantRepository;
import com.snaacker.timeregister.repository.TimesheetRecordRepository;
import com.snaacker.timeregister.repository.UserRepository;
import com.snaacker.timeregister.utils.Constants;
import com.snaacker.timeregister.utils.DtoTransformation;
import com.snaacker.timeregister.utils.Utilities;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired private UserRepository userRepository;
    @Autowired private RestaurantRepository restaurantRepository;
    @Autowired private TimesheetRecordRepository timesheetRecordRepository;

    @Autowired private MessageRepository messageRepository;

    //  @Value("${topic.name}")
    //  private String topicName;

    //  @Autowired
    //  private KafkaTemplate kafkaTemplate;

    public TimeRegisterGenericResponse<UserResponse> getListUser(int startingPage, int pageSize) {

        Pageable pageable = PageRequest.of(startingPage, pageSize);
        List<UserResponse> listUser =
                userRepository.findAll(pageable).stream()
                        .map(DtoTransformation::user2UserResponse)
                        .collect(Collectors.toList());
        return new TimeRegisterGenericResponse<>(listUser, pageSize, startingPage);
    }

    public UserResponse createUser(UserRequest userRequest) throws TimeRegisterBadRequestException {
        User user = DtoTransformation.dto2User(userRequest);
        if (null != userRequest.getRestaurantName()) {
            UserRestaurant userRestaurant = new UserRestaurant();
            userRestaurant.setUsers(user);
            Restaurant returnRestaurant =
                    restaurantRepository.findByName(userRequest.getRestaurantName());
            if (null == returnRestaurant) {
                throw new TimeRegisterBadRequestException("Restaurant does not exist");
            }
            userRestaurant.setRestaurant(returnRestaurant);
            if (user.getRoleName().getRoleValue().equals(Constants.MANAGER)) {
                userRestaurant.setRestaurantManager(true);
            }
        } else {
            logger.warn("You need to assign user to existed restaurant later");
        }
        try {
            user.setPassword(Utilities.encrypt(userRequest.getPassword()));
        } catch (NoSuchPaddingException
                | NoSuchAlgorithmException
                | InvalidKeyException
                | IllegalBlockSizeException
                | BadPaddingException e) {
            throw new TimeRegisterBadRequestException(e.getMessage());
        }
        User createdUser = userRepository.save(user);
        return DtoTransformation.user2UserResponse(createdUser);
    }

    public UserResponse editUser(Long id, UserRequest userRequest)
            throws TimeRegisterBadRequestException {
        User user = userRepository.getById(id);
        if (null != userRequest.getEmail()) {
            user.setEmail(userRequest.getEmail());
        }
        if (null != userRequest.getAddress()) {
            user.setAddress(userRequest.getAddress());
        }
        if (null != userRequest.getFirstName()) {
            user.setFirstName(userRequest.getFirstName());
        }
        if (null != userRequest.getLastName()) {
            user.setLastName(userRequest.getLastName());
        }
        if (null != userRequest.getPhoneNumber()) {
            user.setPhoneNumber(userRequest.getPhoneNumber());
        }
        if (null != userRequest.getRoleName()) {
            user.setRoleName(userRequest.getRoleName());
            if (userRequest.getRoleName().equals("ADMIN")) user.isAdmin();
        }
        if (null != userRequest.getPassword()) {
            try {
                user.setPassword(Utilities.encrypt(userRequest.getPassword()));
            } catch (NoSuchPaddingException
                    | NoSuchAlgorithmException
                    | InvalidKeyException
                    | IllegalBlockSizeException
                    | BadPaddingException e) {
                throw new TimeRegisterBadRequestException(e.getMessage());
            }
        }
        // TODO: too lazy but must fix - timezone or UTC here
        user.setUpdatedDate(new Date());
        User updatedUser = userRepository.save(user);
        return DtoTransformation.user2UserResponse(updatedUser);
    }

    public String deleteUser(long id) throws TimeRegisterBadRequestException {
        User deleteUser =
                userRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new TimeRegisterBadRequestException("User does not exist"));
        userRepository.delete(deleteUser);

        return "OK";
    }

    public UserResponse getUserByName(String username) {
        return DtoTransformation.user2UserResponse(userRepository.findByUsername(username));
    }

    public UserResponse getUserById(Long id) throws TimeRegisterObjectNotFoundException {
        return DtoTransformation.user2UserResponse(
                userRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new TimeRegisterObjectNotFoundException(
                                                "User does not exist")));
    }

    @Transactional
    public UserMultipleTimeRecordResponse addTimeRecord(
            Long id, TimeRecordRequest timeRecordRequest) throws TimeRegisterException {
        User user = userRepository.findById(id).orElseThrow(TimeRegisterException::new);
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
        timesheetRecord.setCreatedDate(new Date());
        timesheetRecord.setUpdatedDate(new Date());
        timesheetRecord.setTimesheetType(timeRecordRequest.getType());
        timesheetRecord.setUsers(user);
        timesheetRecordRepository.save(timesheetRecord);

        Message message = new Message();
        message.setContent("User " + user.getAccountId() + " has submitted a timesheet");
        message.setUsers(user);
        messageRepository.save(message);

        // send message to
        //    UUID key = UUID.randomUUID();
        //    ProducerRecord<String, MessageDto> record = new ProducerRecord<>(topicName,
        //            key.toString(),
        //            new MessageDto("User " + user.getAccountId() + " has submitted a timesheet"));
        //    kafkaTemplate.send(record);
        // TODO: return timesheet record of that week
        List<TimesheetRecord> listTimeSheetRecord =
                timesheetRecordRepository.getTimeRecordOnSavedMonthOfUser(user.getId());

        UserMultipleTimeRecordResponse userMultipleTimeRecordResponse =
                DtoTransformation.Object2UserTimeRecordResponse(listTimeSheetRecord, user);

        return userMultipleTimeRecordResponse;
    }

    public UserMultipleTimeRecordResponse approvedTimesheet(
            Long userId, List<Long> listTimeSheetId) {
        User returnUser = userRepository.getById(userId);
        listTimeSheetId.stream()
                .forEach(
                        timesheetId -> {
                            TimesheetRecord returnTimesheet =
                                    timesheetRecordRepository.getById(timesheetId);
                            returnTimesheet.isApproved();
                            timesheetRecordRepository.save(returnTimesheet);
                        });

        UserMultipleTimeRecordResponse userMultipleTimeRecordResponse =
                new UserMultipleTimeRecordResponse();
        userMultipleTimeRecordResponse.setUser(new UserResponse(returnUser));
        // TODO: get timerecord values and set timerecord here
        userMultipleTimeRecordResponse.setTimeRecords(new TimeRegisterGenericResponse<>());
        return userMultipleTimeRecordResponse;
    }

    public UserSingleTimeRecordResponse editTimeRecord(
            Long userId, Long recordId, TimeRecordRequest timeRecord) {
        // check user existence
        User returnUser = userRepository.getById(userId);

        TimesheetRecord requestChangeTimesheet = timesheetRecordRepository.getById(recordId);
        requestChangeTimesheet.setTimesheetType(timeRecord.getType());
        requestChangeTimesheet.setToTime(timeRecord.getToTime());
        requestChangeTimesheet.setFromTime(timeRecord.getFromTime());
        requestChangeTimesheet.setTimesheetType(timeRecord.getType());

        TimesheetRecord returnTimesheet = timesheetRecordRepository.save(requestChangeTimesheet);
        return new UserSingleTimeRecordResponse(returnUser, returnTimesheet);
    }

    public UserResponse assignRestaurant(Long user_id, Long restaurant_id) {
        User user = userRepository.findById(user_id).get();
        Restaurant restaurant = restaurantRepository.findById(restaurant_id).get();
        UserRestaurant userRestaurant = new UserRestaurant();
        userRestaurant.setUsers(user);
        userRestaurant.setRestaurant(restaurant);
        userRestaurant.setRestaurantManager(
                user.getRoleName().getRoleValue().equals(Constants.MANAGER));
        Set<UserRestaurant> setUserRestaurant = new HashSet<>(Arrays.asList(userRestaurant));
        user.setUserRestaurants(setUserRestaurant);
        User returnUser = userRepository.save(user);
        return new UserResponse(returnUser);
    }
}
