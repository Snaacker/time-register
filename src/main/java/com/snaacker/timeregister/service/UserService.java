package com.snaacker.timeregister.service;

import com.snaacker.timeregister.exception.TimeRegisterBadRequestException;
import com.snaacker.timeregister.exception.TimeRegisterException;
import com.snaacker.timeregister.exception.TimeRegisterObjectNotFoundException;
import com.snaacker.timeregister.model.*;
import com.snaacker.timeregister.persistent.TimesheetRecord;
import com.snaacker.timeregister.persistent.User;
import com.snaacker.timeregister.repository.TimesheetRecordRepository;
import com.snaacker.timeregister.repository.UserRepository;
import com.snaacker.timeregister.utils.Constants;
import com.snaacker.timeregister.utils.DtoTransformation;
import com.snaacker.timeregister.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

  @Autowired private UserRepository userRepository;
  @Autowired private TimesheetRecordRepository timesheetRecordRepository;

  public TimeRegisterGenericResponse<UserResponse> getListUser(int startingPage, int pageSize) {

    Pageable pageable = PageRequest.of(startingPage, pageSize);
    List<UserResponse> listUser =
        userRepository.findAll(pageable).stream()
            .map(user -> DtoTransformation.user2UserResponse(user))
            .collect(Collectors.toList());
    return new TimeRegisterGenericResponse<>(listUser, startingPage, pageSize);
  }

  public UserResponse createUser(UserRequest userRequest) throws TimeRegisterBadRequestException {
    User user = DtoTransformation.dto2User(userRequest);
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

  public String deleteUser(long id)
      throws TimeRegisterBadRequestException {
    User deleteUser =
        userRepository
            .findById(id)
            .orElseThrow(() -> new TimeRegisterBadRequestException("User does not exist"));
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
            .orElseThrow(() -> new TimeRegisterObjectNotFoundException("User does not exist")));
  }

  public UserTimeRecordResponse addTimeRecord(Long id, TimeRecordRequest timeRecordRequest)
      throws TimeRegisterException {
    User user = userRepository.findById(id).orElseThrow(TimeRegisterException::new);
    TimesheetRecord timesheetRecord = new TimesheetRecord();
    timesheetRecord.setUsers(user);
    // TODO: Add logic check same date + check working time (working time constrain)
    timesheetRecord.setToTime(timeRecordRequest.getToTime());
    timesheetRecord.setFromTime(timeRecordRequest.getFromTime());
    timesheetRecordRepository.save(timesheetRecord);

    // TODO: move this to DtoTransformation
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

    // TODO: return timesheet record of that month
    List<TimesheetRecord> listTimeSheetRecord =
        timesheetRecordRepository.getTimeRecordOnSavedMonthOfUser(user);
    List<TimeRecordResponse> timeRecordResponseList =
        listTimeSheetRecord.stream()
            .map(tsr -> new TimeRecordResponse())
            .collect(Collectors.toList());
    userTimeRecordResponse.setTimeRecords(timeRecordResponseList);

    return userTimeRecordResponse;
  }
}
