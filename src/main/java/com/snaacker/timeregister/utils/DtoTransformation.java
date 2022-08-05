package com.snaacker.timeregister.utils;

import com.snaacker.timeregister.model.UserRequestDto;
import com.snaacker.timeregister.persistent.User;

public class DtoTransformation {
    // TODO: these dto transform methods are not needed
    public static UserRequestDto model2Dto(User user) {

        UserRequestDto employeeDto = new UserRequestDto();
        employeeDto.setAddress(user.getAddress());
        employeeDto.setAccountId(user.getAccountId());
        employeeDto.setFirstName(user.getFirstName());
        employeeDto.setLastName(user.getLastName());
        employeeDto.setPhoneNumber(user.getPhoneNumber());
        return employeeDto;
    }

    public static User dto2Model(UserRequestDto requestUserDto) {
        User user = new User();
        user.setAddress(requestUserDto.getAddress());
        user.setAccountId(requestUserDto.getAccountId());
        user.setFirstName(requestUserDto.getFirstName());
        user.setLastName(requestUserDto.getLastName());
        user.setPhoneNumber(requestUserDto.getPhoneNumber());
        return user;
    }
}
