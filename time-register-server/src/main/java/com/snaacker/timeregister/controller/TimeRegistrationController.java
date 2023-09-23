package com.snaacker.timeregister.controller;

import com.snaacker.timeregister.exception.TimeRegisterObjectNotFoundException;
import com.snaacker.timeregister.model.response.TimeRecordResponse;
import com.snaacker.timeregister.model.response.TimeRegisterGenericResponse;
import com.snaacker.timeregister.service.TimesheetRecordService;
import com.snaacker.timeregister.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
public class TimeRegistrationController {

    private TimesheetRecordService timesheetRecordService;

    @Autowired
    public TimeRegistrationController(final TimesheetRecordService timesheetRecordService) {
        this.timesheetRecordService = timesheetRecordService;
    }

    @GetMapping("")
    public ResponseEntity<TimeRegisterGenericResponse<TimeRecordResponse>> getAllTimeRecord(
            @RequestParam(name = "pageSize", defaultValue = Constants.DEFAULT_START_PAGE + "")
                    int startPage,
            @RequestParam(name = "startPage", defaultValue = Constants.DEFAULT_PAGE_SIZE + "")
                    int pageSize) {
        return new ResponseEntity<>(
                timesheetRecordService.getAllTimeRecord(startPage, pageSize), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTimeRecord(@PathVariable long recordId)
            throws TimeRegisterObjectNotFoundException {
        return new ResponseEntity<>(
                timesheetRecordService.deleteTimeRecord(recordId), HttpStatus.OK);
    }
}
