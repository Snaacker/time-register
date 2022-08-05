package com.snaacker.timeregister.service;

import com.snaacker.timeregister.exception.TimeRegisterException;
import com.snaacker.timeregister.model.*;
import com.snaacker.timeregister.persistent.TimesheetRecord;
import com.snaacker.timeregister.persistent.User;
import com.snaacker.timeregister.repository.TimesheetRecordRepository;
import com.snaacker.timeregister.repository.UserRepository;
import com.snaacker.timeregister.utils.DtoTransformation;
import com.snaacker.timeregister.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

  @Autowired private UserRepository userRepository;
  @Autowired private TimesheetRecordRepository timesheetRecordRepository;

  public UserResponseDto loadUserByUsername(String username) throws TimeRegisterException {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new TimeRegisterException("Can not find user");
    }
    return new UserResponseDto(user);
  }

  public UserRequestDto getEmployeeById(long id) {
    User user = userRepository.getById(id);
    return DtoTransformation.model2Dto(user);
  }

  // TODO: return UserDto instead of User
  public Page<User> getListUser(int startingPage, int pageSize) {

    Pageable pageable = PageRequest.of(startingPage, pageSize);
    return userRepository.findAll(pageable);
  }

  public UserRequestDto createUser(UserRequestDto userDto) {
    User user = DtoTransformation.dto2Model(userDto);
    //        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    User createdUser = userRepository.save(user);
    return DtoTransformation.model2Dto(createdUser);
  }

  public UserRequestDto editUser(Long id, UserRequestDto user) {
    // TODO: Not implemented yet
    return null;
  }

  // TODO: return UserDTO instead of User
  public Page<User> deleteUser(long id) {
    User deleteUser = userRepository.getById(id);
    userRepository.delete(deleteUser);

    Pageable pageable = PageRequest.of(0, 20);
    return userRepository.findAll(pageable);
  }

  public User getUserByName(String username) {
    return userRepository.findByUsername(username);
  }

  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }

  public UserTimeRecordResponseDto addTimeRecord(Long id, TimeRecordRequestDto timeRecordRequestDto)
      throws TimeRegisterException {
    User user = userRepository.findById(id).orElseThrow(TimeRegisterException::new);
    TimesheetRecord timesheetRecord = new TimesheetRecord();
    timesheetRecord.setUsers(user);
    // TODO: Add logic check same date + check working time (working time constrain)
    timesheetRecord.setToTime(timeRecordRequestDto.getToTime());
    timesheetRecord.setFromTime(timeRecordRequestDto.getFromTime());
    timesheetRecordRepository.save(timesheetRecord);

    // TODO: move this to DtoTransformation
    UserTimeRecordResponseDto userTimeRecordResponseDto = new UserTimeRecordResponseDto();
    UserResponseDto responseUserDto = new UserResponseDto();
    responseUserDto.setId(user.getId());
    responseUserDto.setAddress(user.getAddress());
    responseUserDto.setAccountId(user.getAccountId());
    responseUserDto.setFirstName(user.getFirstName());
    responseUserDto.setLastName(user.getLastName());
    responseUserDto.setPhoneNumber(user.getPhoneNumber());
    responseUserDto.setRoleName(user.getRoleName());
    userTimeRecordResponseDto.setUser(responseUserDto);

    // TODO: return timesheet record of that month
    List<TimesheetRecord> listTimeSheetRecord =
        timesheetRecordRepository.getTimeRecordOnSavedMonthOfUser(user);
    List<TimeRecordResponseDto> timeRecordResponseDtoList =
        listTimeSheetRecord.stream()
            .map(tsr -> new TimeRecordResponseDto())
            .collect(Collectors.toList());
    userTimeRecordResponseDto.setTimeRecord(timeRecordResponseDtoList);

    return userTimeRecordResponseDto;
  }
}
