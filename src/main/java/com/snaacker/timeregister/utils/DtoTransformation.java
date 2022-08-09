package com.snaacker.timeregister.utils;

import com.snaacker.timeregister.model.TimeRecordResponse;
import com.snaacker.timeregister.model.UserRequest;
import com.snaacker.timeregister.model.UserResponse;
import com.snaacker.timeregister.persistent.TimesheetRecord;
import com.snaacker.timeregister.persistent.User;

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
}
