package com.snaacker.timeregister.service;

import com.snaacker.timeregister.exception.TimeRegisterBadRequestException;
import com.snaacker.timeregister.model.ScheduleRequest;
import com.snaacker.timeregister.model.ScheduleResponse;
import com.snaacker.timeregister.model.TimeRegisterGenericResponse;
import com.snaacker.timeregister.persistent.Restaurant;
import com.snaacker.timeregister.persistent.Schedule;
import com.snaacker.timeregister.repository.RestaurantRepository;
import com.snaacker.timeregister.repository.ScheduleRepository;
import com.snaacker.timeregister.utils.DtoTransformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ScheduleService {

  @Autowired private ScheduleRepository scheduleRepository;
  @Autowired private RestaurantRepository restaurantRepository;

  public TimeRegisterGenericResponse<ScheduleResponse> getScheduleOfRestaurant(int restaurantId) {
//    TODO: Finish me
    return null;
  }

  public ScheduleResponse createSchedule(Long id, ScheduleRequest scheduleRequest) {
    Restaurant restaurant = restaurantRepository.getById(id);
    if (null == restaurant) {
      throw new TimeRegisterBadRequestException("Restaurant does not exist");
    }
    Schedule newSchedule = new Schedule();
    newSchedule.setScheduleDate(scheduleRequest.getScheduleDate());
    newSchedule.setFromTime(scheduleRequest.getFromTime());
    newSchedule.setToTime(scheduleRequest.getToTime());
    Schedule schedule = scheduleRepository.save(newSchedule);
    return new ScheduleResponse(schedule);
  }

  public ScheduleResponse editSchedule(Long id, Long schedule_id, ScheduleRequest scheduleRequest) {
    Restaurant restaurant = restaurantRepository.getById(id);
    if (null == restaurant) {
      throw new TimeRegisterBadRequestException("Restaurant does not exist");
    }
    Schedule schedule = scheduleRepository.getById(schedule_id);
    if (null == schedule){
      throw new TimeRegisterBadRequestException("Schedule record does not exist");
    }
    if (null != scheduleRequest.getScheduleDate()){
      schedule.setScheduleDate(scheduleRequest.getScheduleDate());
    }
    if (null != scheduleRequest.getToTime()){
      schedule.setToTime(scheduleRequest.getToTime());
    }
    if (null != scheduleRequest.getFromTime()){
      schedule.setFromTime(scheduleRequest.getFromTime());
    }
    schedule.setUpdatedDate(new Date());
    return new ScheduleResponse(scheduleRepository.save(schedule));
  }

  //  public TimeRegisterGenericResponse<UserResponse> getListUser(int startingPage, int pageSize) {
  //
  //    Pageable pageable = PageRequest.of(startingPage, pageSize);
  //    List<UserResponse> listUser =
  //        userRepository.findAll(pageable).stream()
  //            .map(DtoTransformation::user2UserResponse)
  //            .collect(Collectors.toList());
  //    return new TimeRegisterGenericResponse<>(listUser, startingPage, pageSize);
  //  }
  //
  //  public UserResponse createUser(UserRequest userRequest) throws TimeRegisterBadRequestException
  // {
  //    User user = DtoTransformation.dto2User(userRequest);
  //    try {
  //      user.setPassword(Utilities.encrypt(userRequest.getPassword()));
  //    } catch (NoSuchPaddingException
  //        | NoSuchAlgorithmException
  //        | InvalidKeyException
  //        | IllegalBlockSizeException
  //        | BadPaddingException e) {
  //      throw new TimeRegisterBadRequestException(e.getMessage());
  //    }
  //    User createdUser = userRepository.save(user);
  //    return DtoTransformation.user2UserResponse(createdUser);
  //  }
  //
  //  public UserResponse editUser(Long id, UserRequest userRequest)
  //      throws TimeRegisterBadRequestException {
  //    User user = userRepository.getById(id);
  //    if (null != userRequest.getEmail()) {
  //      user.setEmail(userRequest.getEmail());
  //    }
  //    if (null != userRequest.getAddress()) {
  //      user.setAddress(userRequest.getAddress());
  //    }
  //    if (null != userRequest.getFirstName()) {
  //      user.setFirstName(userRequest.getFirstName());
  //    }
  //    if (null != userRequest.getLastName()) {
  //      user.setLastName(userRequest.getLastName());
  //    }
  //    if (null != userRequest.getPhoneNumber()) {
  //      user.setPhoneNumber(userRequest.getPhoneNumber());
  //    }
  //    if (null != userRequest.getRoleName()) {
  //      user.setRoleName(userRequest.getRoleName());
  //      if (userRequest.getRoleName().equals("ADMIN")) user.isAdmin();
  //    }
  //    if (null != userRequest.getPassword()) {
  //      try {
  //        user.setPassword(Utilities.encrypt(userRequest.getPassword()));
  //      } catch (NoSuchPaddingException
  //          | NoSuchAlgorithmException
  //          | InvalidKeyException
  //          | IllegalBlockSizeException
  //          | BadPaddingException e) {
  //        throw new TimeRegisterBadRequestException(e.getMessage());
  //      }
  //    }
  //    // TODO: too lazy but must fix - timezone or UTC here
  //    user.setUpdatedDate(new Date());
  //    User updatedUser = userRepository.save(user);
  //    return DtoTransformation.user2UserResponse(updatedUser);
  //  }
  //
  //  public String deleteUser(long id) throws TimeRegisterBadRequestException {
  //    User deleteUser =
  //        userRepository
  //            .findById(id)
  //            .orElseThrow(() -> new TimeRegisterBadRequestException("User does not exist"));
  //    userRepository.delete(deleteUser);
  //
  //    return "OK";
  //  }
  //
  //  public UserResponse getUserByName(String username) {
  //    return DtoTransformation.user2UserResponse(userRepository.findByUsername(username));
  //  }
  //
  //  public UserResponse getUserById(Long id) throws TimeRegisterObjectNotFoundException {
  //    return DtoTransformation.user2UserResponse(
  //        userRepository
  //            .findById(id)
  //            .orElseThrow(() -> new TimeRegisterObjectNotFoundException("User does not exist")));
  //  }
  //
  //  public UserTimeRecordResponse addTimeRecord(Long id, TimeRecordRequest timeRecordRequest)
  //      throws TimeRegisterException {
  //    User user = userRepository.findById(id).orElseThrow(TimeRegisterException::new);
  //    TimesheetRecord timesheetRecord = new TimesheetRecord();
  //    timesheetRecord.setUsers(user);
  //    // TODO: Add logic check working time (working time constrain)
  //
  //    if (!Utilities.isSameDay(timeRecordRequest.getToTime(), timeRecordRequest.getFromTime())) {
  //      throw new TimeRegisterException("Register time should be in a same date");
  //    }
  //    timesheetRecord.setToTime(timeRecordRequest.getToTime());
  //    timesheetRecord.setFromTime(timeRecordRequest.getFromTime());
  //    timesheetRecord.setCreatedDate(new Date());
  //    timesheetRecord.setUpdatedDate(new Date());
  //    timesheetRecord.setTimesheetType(timeRecordRequest.getType());
  //    // TODO: Add logic check overlap time
  //    timesheetRecordRepository.save(timesheetRecord);
  //
  //    // TODO: return timesheet record of that month
  //    List<TimesheetRecord> listTimeSheetRecord =
  //        timesheetRecordRepository.getTimeRecordOnSavedMonthOfUser(user.getId());
  //
  //    UserTimeRecordResponse userTimeRecordResponse =
  //        DtoTransformation.Object2UserTimeRecordResponse(listTimeSheetRecord, user);
  //
  //    return userTimeRecordResponse;
  //  }
}
