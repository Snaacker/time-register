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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimesheetRecordService {

  private TimesheetRecordRepository timesheetRecordRepository;
  private UserRepository userRepository;

  @Autowired
  public TimesheetRecordService(
      final TimesheetRecordRepository timesheetRecordRepository,
      final UserRepository userRepository) {
    this.timesheetRecordRepository = timesheetRecordRepository;
    this.userRepository = userRepository;
  }

  public TimeRegisterGenericResponse<TimeRecordResponse> getTimeRecordByUserId(
      long userId, Date fromDate, Date toDate, int startPage, int pageSize)
      throws TimeRegisterObjectNotFoundException {
    userRepository
        .findById(userId)
        .orElseThrow(() -> new TimeRegisterObjectNotFoundException("User does not exist"));
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new TimeRegisterObjectNotFoundException("User not found"));
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    ;
    List<TimeRecordResponse> listTimeRecordResponse =
        timesheetRecordRepository
            .findRecordInTimeRange(
                dateFormat.format(fromDate), dateFormat.format(toDate), user.getId())
            .stream()
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
