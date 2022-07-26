package com.snaacker.timeregister.controller;

import com.snaacker.timeregister.model.RequestUserDto;
import com.snaacker.timeregister.service.UserService;
import com.snaacker.timeregister.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
  private static final int PAGE_SIZE = 50;
  @Autowired private UserService userService;

  @GetMapping("/{id}")
  public RequestUserDto getUserById(@PathVariable Long id) {
    return userService.getEmployeeById(id);
  }

  @GetMapping("")
  public List<RequestUserDto> getListUser(
      @RequestParam("startPage") int startPage, @RequestParam("pageSize") int pageSize) {
    if (pageSize == 0) pageSize = PAGE_SIZE;
    return userService.getListUser(startPage, pageSize).stream()
        .map(user -> Utilities.model2Dto(user))
        .collect(Collectors.toList());
  }

  @PutMapping("")
  public RequestUserDto createUser(@RequestBody RequestUserDto user) {
    return userService.createUser(user);
  }

  @PostMapping("")
  public RequestUserDto editUser(@RequestBody RequestUserDto user) {
    // Not implemented yet
    return null;
  }

  @DeleteMapping("/{id}")
  public List<RequestUserDto> deleteUser(@PathVariable Long id) {
    // TODO: Not implemented yet
    return null;
  }

  // TODO: return list registed time of user
  @GetMapping("/{id}/registed")
  public List<Object> getAllRegistedTimeByUser() {
    return null;
  }
}
