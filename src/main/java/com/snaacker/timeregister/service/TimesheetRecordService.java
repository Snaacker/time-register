package com.snaacker.timeregister.service;

import com.snaacker.timeregister.exception.TimeRegisterObjectNotFoundException;
import com.snaacker.timeregister.model.TimeRecordResponse;
import com.snaacker.timeregister.model.TimeRegisterGenericResponse;
import com.snaacker.timeregister.persistent.TimesheetRecord;
import com.snaacker.timeregister.persistent.User;
import com.snaacker.timeregister.repository.TimesheetRecordRepository;
import com.snaacker.timeregister.repository.UserRepository;
import com.snaacker.timeregister.utils.DtoTransformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimesheetRecordService {

  @Autowired TimesheetRecordRepository timesheetRecordRepository;
  @Autowired UserRepository userRepository;

  public TimeRegisterGenericResponse<TimeRecordResponse> getTimeRecordByUserId(
      long userId, Date fromDate, Date toDate, int startPage, int pageSize)
      throws TimeRegisterObjectNotFoundException {
    userRepository
        .findById(userId)
        .orElseThrow(() -> new TimeRegisterObjectNotFoundException("User does not exist"));
    Pageable pageable = PageRequest.of(startPage, pageSize);
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new TimeRegisterObjectNotFoundException("User not found"));
    // TODO: Use SQL condition for better perfomance
    List<TimeRecordResponse> listTimeRecordResponse =
        timesheetRecordRepository.findByUsers(user, pageable).stream()
            .filter(x -> x.getFromTime().after(fromDate) && x.getToTime().before(toDate))
            .map(
                timesheetRecord ->
                    DtoTransformation.timesheetRecord2TimeRecordResponse(timesheetRecord))
            .collect(Collectors.toList());
    return new TimeRegisterGenericResponse(listTimeRecordResponse, startPage, pageSize);
  }

  public TimeRegisterGenericResponse<TimeRecordResponse> getAllTimeRecord(
      int startPage, int pageSize) {
    Pageable pageable = PageRequest.of(startPage, pageSize);
    List<TimeRecordResponse> listTimeRecordResponse =
        timesheetRecordRepository.findAll(pageable).stream()
            .map(DtoTransformation::timesheetRecord2TimeRecordResponse)
            .collect(Collectors.toList());
    return new TimeRegisterGenericResponse<>(listTimeRecordResponse, startPage, pageSize);
  }

  public String deleteTimeRecord(long recordId) throws TimeRegisterObjectNotFoundException {
    TimesheetRecord timesheetRecord =
        timesheetRecordRepository
            .findById(recordId)
            .orElseThrow(() -> new TimeRegisterObjectNotFoundException("Record does not exist"));
    timesheetRecordRepository.delete(timesheetRecord);
    return "OK";
  }
}
