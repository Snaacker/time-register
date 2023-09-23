package com.snaacker.timeregister.utils;

import com.snaacker.timeregister.model.UserRestaurantDto;
import com.snaacker.timeregister.model.request.RestaurantRequest;
import com.snaacker.timeregister.model.response.EmployeeResponse;
import com.snaacker.timeregister.model.response.TimeRecordResponse;
import com.snaacker.timeregister.model.response.TimeRegisterGenericResponse;
import com.snaacker.timeregister.model.response.UserMultipleTimeRecordResponse;
import com.snaacker.timeregister.persistent.Employee;
import com.snaacker.timeregister.persistent.EmployeeRestaurant;
import com.snaacker.timeregister.persistent.Restaurant;
import com.snaacker.timeregister.persistent.TimesheetRecord;
import java.util.List;
import java.util.stream.Collectors;

public class DtoTransformation {

    public static EmployeeResponse user2UserResponse(Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setAddress(employee.getAddress());
        employeeResponse.setAccountId(employee.getAccountId());
        employeeResponse.setFirstName(employee.getFirstName());
        employeeResponse.setLastName(employee.getLastName());
        employeeResponse.setPhoneNumber(employee.getPhoneNumber());
        employeeResponse.setRoleName(employee.getRoleName());
        employeeResponse.setId(employee.getId());
        employeeResponse.setUsername(employee.getAccountName());
        employeeResponse.setMaximumWorkingHours(
                employee.getContract().getMaximumWorkingHoursPerWeek());
        employeeResponse.setEmail(employee.getEmail());
        return employeeResponse;
    }

    public static TimeRecordResponse timesheetRecord2TimeRecordResponse(
            TimesheetRecord timesheetRecord) {
        TimeRecordResponse timeRecordResponse = new TimeRecordResponse();
        timeRecordResponse.setToTime(timesheetRecord.getToTime());
        timeRecordResponse.setFromTime(timesheetRecord.getFromTime());
        return timeRecordResponse;
    }

    public static UserMultipleTimeRecordResponse Object2UserTimeRecordResponse(
            List<TimesheetRecord> listTimeSheetRecord, Employee employee) {
        UserMultipleTimeRecordResponse userMultipleTimeRecordResponse =
                new UserMultipleTimeRecordResponse();
        EmployeeResponse responseUser = new EmployeeResponse();
        responseUser.setId(employee.getId());
        responseUser.setAddress(employee.getAddress());
        responseUser.setAccountId(employee.getAccountId());
        responseUser.setFirstName(employee.getFirstName());
        responseUser.setLastName(employee.getLastName());
        responseUser.setPhoneNumber(employee.getPhoneNumber());
        responseUser.setRoleName(employee.getRoleName());
        userMultipleTimeRecordResponse.setUser(responseUser);

        List<TimeRecordResponse> timeRecordResponseList =
                listTimeSheetRecord.stream()
                        .map(DtoTransformation::TimeRecord2TimeRecordResponse)
                        .collect(Collectors.toList());

        userMultipleTimeRecordResponse.setTimeRecords(
                new TimeRegisterGenericResponse<TimeRecordResponse>(
                        timeRecordResponseList,
                        Constants.DEFAULT_PAGE_SIZE,
                        Constants.DEFAULT_START_PAGE));
        return userMultipleTimeRecordResponse;
    }

    public static TimeRecordResponse TimeRecord2TimeRecordResponse(TimesheetRecord tsr) {
        TimeRecordResponse timeRecordResponse = new TimeRecordResponse();
        timeRecordResponse.setToTime(tsr.getToTime());
        timeRecordResponse.setFromTime(tsr.getFromTime());
        timeRecordResponse.setTimesheetType(tsr.getTimesheetType());
        return timeRecordResponse;
    }

    public static Restaurant restaurantRequest2Restaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.getName());
        restaurant.setAddress(restaurantRequest.getAddress());
        if (null != restaurantRequest.getUserRestaurantDto()) {
            restaurant.setEmployeeRestaurant(
                    restaurantRequest.getUserRestaurantDto().stream()
                            .map(DtoTransformation::UserRestaurantDto2UserRestaurant)
                            .collect(Collectors.toSet()));
        }
        return restaurant;
    }

    private static EmployeeRestaurant UserRestaurantDto2UserRestaurant(
            UserRestaurantDto userRestaurantDto) {
        EmployeeRestaurant employeeRestaurant = new EmployeeRestaurant();

        //    userRestaurant.setUsers();
        employeeRestaurant.setRestaurantManager(userRestaurantDto.isManager());
        return employeeRestaurant;
    }
}
