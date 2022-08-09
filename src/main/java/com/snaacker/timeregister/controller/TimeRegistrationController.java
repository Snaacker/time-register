package com.snaacker.timeregister.controller;

import com.snaacker.timeregister.exception.TimeRegisterObjectNotFoundException;
import com.snaacker.timeregister.model.TimeRecordResponse;
import com.snaacker.timeregister.model.TimeRegisterGenericResponse;
import com.snaacker.timeregister.service.TimesheetRecordService;
import com.snaacker.timeregister.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
public class TimeRegistrationController {

  @Autowired TimesheetRecordService timesheetRecordService;

  @GetMapping("")
  public ResponseEntity<TimeRegisterGenericResponse<TimeRecordResponse>> getAllTimeRecord(
      @RequestParam(name = "startPage", defaultValue = Constants.DEFAULT_START_PAGE + "")
          int startPage,
      @RequestParam(name = "pageSize", defaultValue = "0") int pageSize) {
    return new ResponseEntity<>(
        timesheetRecordService.getAllTimeRecord(startPage, pageSize), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteTimeRecord(@PathVariable long recordId) throws TimeRegisterObjectNotFoundException {
    return new ResponseEntity<>(timesheetRecordService.deleteTimeRecord(recordId), HttpStatus.OK);
  }
}
