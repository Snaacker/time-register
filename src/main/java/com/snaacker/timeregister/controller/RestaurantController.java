package com.snaacker.timeregister.controller;

import com.snaacker.timeregister.annotation.AllowAdmin;
import com.snaacker.timeregister.model.RestaurantResponse;
import com.snaacker.timeregister.model.ScheduleRequest;
import com.snaacker.timeregister.model.ScheduleResponse;
import com.snaacker.timeregister.model.TimeRegisterGenericResponse;
import com.snaacker.timeregister.service.RestaurantService;
import com.snaacker.timeregister.service.ScheduleService;
import com.snaacker.timeregister.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/restaurant")
public class RestaurantController {

  @Autowired RestaurantService restaurantService;
  @Autowired
  ScheduleService scheduleService;

  @GetMapping("/")
  public ResponseEntity<TimeRegisterGenericResponse<RestaurantResponse>> getListRestaurant(
      @RequestParam(name = "startPage", defaultValue = Constants.DEFAULT_START_PAGE + "")
          int startPage,
      @RequestParam(name = "pageSize", defaultValue = "0") int pageSize) {
    return new ResponseEntity<>(
        restaurantService.getAllRestaurant(startPage, pageSize), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable Long id) {
    return new ResponseEntity<>(restaurantService.getRestaurantById(id), HttpStatus.OK);
  }

  @PutMapping("/")
  public ResponseEntity<RestaurantResponse> createRestaurant() {
    // TODO: Not implement yet
    return null;
  }

  @PostMapping("/{id}")
  public ResponseEntity<RestaurantResponse> editRestaurant(@PathVariable Long id) {
    // TODO: Not implement yet
    return null;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteRestaurant(@PathVariable Long id) {
    return new ResponseEntity<>(restaurantService.deleteRestaurant(id), HttpStatus.OK);
  }

  @AllowAdmin
  @PutMapping("/{id}/schedule")
  public ResponseEntity<ScheduleResponse> createSchedule(
      @PathVariable Long id, @RequestBody ScheduleRequest scheduleRequest) {
    return new ResponseEntity<>(scheduleService.createSchedule(id, scheduleRequest), HttpStatus.CREATED);
  }

  @AllowAdmin
  @PostMapping("/{id}/schedule/{schedule_id}")
  public ResponseEntity<ScheduleResponse> editSchedule(
      @PathVariable Long id, @PathVariable Long schedule_id) {
    // TODO: Not implemented yet
    return null;
  }
}
