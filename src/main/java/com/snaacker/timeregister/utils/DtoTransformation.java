package com.snaacker.timeregister.utils;

import com.snaacker.timeregister.model.*;
import com.snaacker.timeregister.persistent.Restaurant;
import com.snaacker.timeregister.persistent.TimesheetRecord;
import com.snaacker.timeregister.persistent.User;

import java.util.List;
import java.util.stream.Collectors;

public class DtoTransformation {
  // TODO: these dto transform methods are not needed
  public static UserRequest model2Dto(User user) {

    UserRequest userRequest = new UserRequest();
    userRequest.setAddress(user.getAddress());
    userRequest.setAccountId(user.getAccountId());
    userRequest.setFirstName(user.getFirstName());
    userRequest.setLastName(user.getLastName());
    userRequest.setPhoneNumber(user.getPhoneNumber());
    return userRequest;
  }

  public static UserResponse user2UserResponse(User user) {

    UserResponse userResponse = new UserResponse();
    userResponse.setAddress(user.getAddress());
    userResponse.setAccountId(user.getAccountId());
    userResponse.setFirstName(user.getFirstName());
    userResponse.setLastName(user.getLastName());
    userResponse.setPhoneNumber(user.getPhoneNumber());
    userResponse.setRoleName(user.getRoleName());
    userResponse.setId(user.getId());
    userResponse.setUsername(user.getUsername());
    return userResponse;
  }

  public static User dto2User(UserRequest requestUser) {
    User user = new User();
    user.setAddress(requestUser.getAddress());
    user.setAccountId(requestUser.getAccountId());
    user.setFirstName(requestUser.getFirstName());
    user.setLastName(requestUser.getLastName());
    user.setPhoneNumber(requestUser.getPhoneNumber());
    return user;
  }

  public static TimeRecordResponse timesheetRecord2TimeRecordResponse(
      TimesheetRecord timesheetRecord) {
    TimeRecordResponse timeRecordResponse = new TimeRecordResponse();
    timeRecordResponse.setToTime(timesheetRecord.getToTime());
    timeRecordResponse.setFromTime(timesheetRecord.getFromTime());
    return timeRecordResponse;
  }

  public static UserTimeRecordResponse Object2UserTimeRecordResponse(
      List<TimesheetRecord> listTimeSheetRecord, User user) {
    UserTimeRecordResponse userTimeRecordResponse = new UserTimeRecordResponse();
    UserResponse responseUser = new UserResponse();
    responseUser.setId(user.getId());
    responseUser.setAddress(user.getAddress());
    responseUser.setAccountId(user.getAccountId());
    responseUser.setFirstName(user.getFirstName());
    responseUser.setLastName(user.getLastName());
    responseUser.setPhoneNumber(user.getPhoneNumber());
    responseUser.setRoleName(user.getRoleName());
    userTimeRecordResponse.setUser(responseUser);

    List<TimeRecordResponse> timeRecordResponseList =
        listTimeSheetRecord.stream()
            .map(DtoTransformation::TimeRecord2TimeRecordResponse)
            .collect(Collectors.toList());

    userTimeRecordResponse.setTimeRecords(
        new TimeRegisterGenericResponse<TimeRecordResponse>(
            timeRecordResponseList, Constants.DEFAULT_PAGE_SIZE, Constants.DEFAULT_START_PAGE));
    return userTimeRecordResponse;
  }

  public static TimeRecordResponse TimeRecord2TimeRecordResponse(TimesheetRecord tsr) {
    TimeRecordResponse timeRecordResponse = new TimeRecordResponse();
    timeRecordResponse.setToTime(tsr.getToTime());
    timeRecordResponse.setFromTime(tsr.getFromTime());
    timeRecordResponse.setTimesheetType(tsr.getTimesheetType());
    return timeRecordResponse;
  }

}
