package com.snaacker.timeregister.utils;

import com.snaacker.timeregister.model.RequestUserDto;
import com.snaacker.timeregister.persistent.User;

public class Utilities {

  public static RequestUserDto model2Dto(User user) {

    RequestUserDto employeeDto = new RequestUserDto();
    employeeDto.setAddress(user.getAddress());
    employeeDto.setAccountId(user.getAccountId());
    employeeDto.setFirstName(user.getFirstName());
    employeeDto.setLastName(user.getLastName());
    employeeDto.setPhoneNumber(user.getPhoneNumber());
    return employeeDto;
  }

  public static User dto2Model(RequestUserDto requestUserDto) {
    User user = new User();
    user.setAddress(requestUserDto.getAddress());
    user.setAccountId(requestUserDto.getAccountId());
    user.setFirstName(requestUserDto.getFirstName());
    user.setLastName(requestUserDto.getLastName());
    user.setPhoneNumber(requestUserDto.getPhoneNumber());
    if (null != requestUserDto.getId()) {
      user.setId(requestUserDto.getId());
    }
    return user;
  }
}
