package com.snaacker.timeregister.controller;

import com.snaacker.timeregister.annotation.AllowAdmin;
import com.snaacker.timeregister.exception.TimeRegisterBadRequestException;
import com.snaacker.timeregister.exception.TimeRegisterException;
import com.snaacker.timeregister.exception.TimeRegisterObjectNotFoundException;
import com.snaacker.timeregister.model.*;
import com.snaacker.timeregister.persistent.UserRestaurant;
import com.snaacker.timeregister.service.TimesheetRecordService;
import com.snaacker.timeregister.service.UserService;
import com.snaacker.timeregister.utils.Constants;
import com.snaacker.timeregister.utils.DtoTransformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
  @Autowired private UserService userService;
  @Autowired TimesheetRecordService timesheetRecordService;

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
      @RequestParam(name = "pageSize", defaultValue = "0") int pageSize) {
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
  public ResponseEntity<UserResponse> editUser(@PathVariable long id, @RequestBody UserRequest user)
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
  public ResponseEntity<TimeRegisterGenericResponse<TimeRecordResponse>> getAllRegistedTimeByUser(
      @RequestParam(name = "startPage", defaultValue = Constants.DEFAULT_START_PAGE + "")
          int startPage,
      @RequestParam(name = "pageSize", defaultValue = "0") int pageSize,
      @PathVariable long id,
      @RequestParam Date fromDate,
      @RequestParam Date toDate)
      throws TimeRegisterObjectNotFoundException {

    return new ResponseEntity<>(
        timesheetRecordService.getTimeRecordByUserId(id, fromDate, toDate, startPage, pageSize),
        HttpStatus.OK);
  }

  @PutMapping("/{id}/registration")
  public ResponseEntity<UserTimeRecordResponse> addTimeRecord(
      @PathVariable Long id, @RequestBody TimeRecordRequest timeRecordRequest)
      throws TimeRegisterException {
    return new ResponseEntity<>(
        userService.addTimeRecord(id, timeRecordRequest), HttpStatus.CREATED);
  }

  @PostMapping("/{id}/registration/{record_id}")
  public ResponseEntity<UserTimeRecordResponse> editTimeRecord(
      @PathVariable Long id, @PathVariable Long record_id) {
    return null;
  }
}
