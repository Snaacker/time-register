package com.snaacker.timeregister.controller;

import com.snaacker.timeregister.annotation.AllowAdmin;
import com.snaacker.timeregister.exception.TimeRegisterBadRequestException;
import com.snaacker.timeregister.exception.TimeRegisterException;
import com.snaacker.timeregister.exception.TimeRegisterObjectNotFoundException;
import com.snaacker.timeregister.model.request.TimeRecordRequest;
import com.snaacker.timeregister.model.request.UserRequest;
import com.snaacker.timeregister.model.response.TimeRecordResponse;
import com.snaacker.timeregister.model.response.TimeRegisterGenericResponse;
import com.snaacker.timeregister.model.response.UserMultipleTimeRecordResponse;
import com.snaacker.timeregister.model.response.UserResponse;
import com.snaacker.timeregister.model.response.UserSingleTimeRecordResponse;
import com.snaacker.timeregister.service.TimesheetRecordService;
import com.snaacker.timeregister.service.UserService;
import com.snaacker.timeregister.utils.Constants;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private UserService userService;
    private TimesheetRecordService timesheetRecordService;

    @Autowired
    public UserController(
            final UserService userService, final TimesheetRecordService timesheetRecordService) {
        this.userService = userService;
        this.timesheetRecordService = timesheetRecordService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id)
            throws TimeRegisterObjectNotFoundException {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @AllowAdmin
    @GetMapping("")
    public ResponseEntity<TimeRegisterGenericResponse<UserResponse>> getListUser(
            @RequestParam(name = "startPage", defaultValue = Constants.DEFAULT_START_PAGE + "")
                    int startPage,
            @RequestParam(name = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE + "")
                    int pageSize) {
        return new ResponseEntity<>(userService.getListUser(startPage, pageSize), HttpStatus.OK);
    }

    @AllowAdmin
    @PutMapping("")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest user)
            throws TimeRegisterBadRequestException {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @AllowAdmin
    @PostMapping("/{id}")
    public ResponseEntity<UserResponse> editUser(
            @PathVariable long id, @RequestBody UserRequest user)
            throws TimeRegisterBadRequestException {
        return new ResponseEntity<>(userService.editUser(id, user), HttpStatus.OK);
    }

    @AllowAdmin
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id)
            throws TimeRegisterBadRequestException {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }

    @AllowAdmin
    // TODO: return list registed time of user
    @GetMapping("/{id}/registration")
    public ResponseEntity<TimeRegisterGenericResponse<TimeRecordResponse>>
            getAllRegisteredTimeByUser(
                    @RequestParam(
                                    name = "startPage",
                                    defaultValue = Constants.DEFAULT_START_PAGE + "")
                            int startPage,
                    @RequestParam(
                                    name = "pageSize",
                                    defaultValue = Constants.DEFAULT_PAGE_SIZE + "")
                            int pageSize,
                    @PathVariable long id,
                    @RequestParam Date fromDate,
                    @RequestParam Date toDate)
                    throws TimeRegisterObjectNotFoundException {

        return new ResponseEntity<>(
                timesheetRecordService.getTimeRecordByUserId(
                        id, fromDate, toDate, startPage, pageSize),
                HttpStatus.OK);
    }

    @PutMapping("/{id}/registration")
    public ResponseEntity<UserMultipleTimeRecordResponse> addTimeRecord(
            @PathVariable Long id, @RequestBody TimeRecordRequest timeRecordRequest)
            throws TimeRegisterException {
        return new ResponseEntity<>(
                userService.addTimeRecord(id, timeRecordRequest), HttpStatus.CREATED);
    }

    @PostMapping("/{user_id}/time_record/{record_id}")
    public ResponseEntity<UserSingleTimeRecordResponse> editTimeRecord(
            @PathVariable Long user_id,
            @PathVariable Long record_id,
            @RequestBody TimeRecordRequest timeRecord) {
        return new ResponseEntity<>(
                userService.editTimeRecord(user_id, record_id, timeRecord), HttpStatus.ACCEPTED);
    }

    @AllowAdmin
    @PostMapping("/{user_id}/confirmation")
    public ResponseEntity<String> approveTimeSheet(
            @PathVariable Long user_id, @RequestBody List<Long> listTimeSheetId) {
        userService.approvedTimesheet(user_id, listTimeSheetId);
        // TODO: return user response
        return new ResponseEntity<>("Success approved", HttpStatus.OK);
    }
}
