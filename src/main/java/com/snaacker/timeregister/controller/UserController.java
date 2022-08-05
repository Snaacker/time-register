package com.snaacker.timeregister.controller;

import com.snaacker.timeregister.exception.TimeRegisterException;
import com.snaacker.timeregister.model.UserRequestDto;
import com.snaacker.timeregister.model.TimeRecordRequestDto;
import com.snaacker.timeregister.model.TimeRecordResponseDto;
import com.snaacker.timeregister.model.UserTimeRecordResponseDto;
import com.snaacker.timeregister.service.UserService;
import com.snaacker.timeregister.utils.DtoTransformation;
import com.snaacker.timeregister.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
  private static final int PAGE_SIZE = 50;
  @Autowired private UserService userService;

  @GetMapping("/{id}")
  public ResponseEntity<UserRequestDto> getUserById(@PathVariable Long id) {
    return new ResponseEntity<>(userService.getEmployeeById(id), HttpStatus.OK);
  }

  @GetMapping("")
  public ResponseEntity<List<UserRequestDto>> getListUser(
      @RequestParam("startPage") int startPage, @RequestParam("pageSize") int pageSize) {
    if (pageSize == 0) pageSize = PAGE_SIZE;
    return new ResponseEntity<>(
        userService.getListUser(startPage, pageSize).stream()
            .map(user -> DtoTransformation.model2Dto(user))
            .collect(Collectors.toList()),
        HttpStatus.OK);
  }

  @PutMapping("")
  public ResponseEntity<UserRequestDto> createUser(@RequestBody UserRequestDto user) {
    return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
  }

  @PostMapping("")
  public UserRequestDto editUser(@RequestBody UserRequestDto user) {
    // Not implemented yet
    return null;
  }

  @DeleteMapping("/{id}")
  public List<UserRequestDto> deleteUser(@PathVariable Long id) {
    // TODO: Not implemented yet
    return null;
  }

  // TODO: return list registed time of user
  @GetMapping("/{id}/registration")
  public ResponseEntity<List<TimeRecordResponseDto>> getAllRegistedTimeByUser() {
    return null;
  }

  @PutMapping("/{id}/registration")
  public ResponseEntity<UserTimeRecordResponseDto> addTimeRecord(
      @PathVariable Long id, @RequestBody TimeRecordRequestDto timeRecordRequestDto)
      throws TimeRegisterException {
    return new ResponseEntity<>(userService.addTimeRecord(id, timeRecordRequestDto), HttpStatus.CREATED);
  }

  @PostMapping("/{id}/registration")
  public UserTimeRecordResponseDto editTime() {
    return null;
  }
}
