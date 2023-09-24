package com.snaacker.timeregister.controller;

import com.snaacker.timeregister.annotation.AllowAdmin;
import com.snaacker.timeregister.model.request.RestaurantRequest;
import com.snaacker.timeregister.model.request.ScheduleRequest;
import com.snaacker.timeregister.model.response.EmployeeResponse;
import com.snaacker.timeregister.model.response.RestaurantResponse;
import com.snaacker.timeregister.model.response.ScheduleResponse;
import com.snaacker.timeregister.model.response.TimeRegisterGenericResponse;
import com.snaacker.timeregister.service.RestaurantService;
import com.snaacker.timeregister.service.ScheduleService;
import com.snaacker.timeregister.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final ScheduleService scheduleService;

    @Autowired
    public RestaurantController(
            final RestaurantService restaurantService, final ScheduleService scheduleService) {
        this.restaurantService = restaurantService;
        this.scheduleService = scheduleService;
    }

    @AllowAdmin
    @GetMapping("")
    public ResponseEntity<TimeRegisterGenericResponse<RestaurantResponse>> getListRestaurant(
            @RequestParam(name = "startPage", defaultValue = Constants.DEFAULT_START_PAGE + "")
                    int startPage,
            @RequestParam(name = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE + "")
                    int pageSize) {
        return new ResponseEntity<>(
                restaurantService.getAllRestaurant(startPage, pageSize), HttpStatus.OK);
    }

    @AllowAdmin
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable Long id) {
        return new ResponseEntity<>(restaurantService.getRestaurantById(id), HttpStatus.OK);
    }

    @AllowAdmin
    @PutMapping("")
    public ResponseEntity<RestaurantResponse> createRestaurant(
            @RequestBody RestaurantRequest restaurantRequest) {
        return new ResponseEntity<>(
                restaurantService.createRestaurant(restaurantRequest), HttpStatus.CREATED);
    }

    @AllowAdmin
    @PostMapping("/{id}")
    public ResponseEntity<RestaurantResponse> editRestaurant(
            @PathVariable Long id, @RequestBody RestaurantRequest restaurantRequest) {
        return new ResponseEntity<>(
                restaurantService.editRestaurant(id, restaurantRequest), HttpStatus.OK);
    }

    @AllowAdmin
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long id) {
        return new ResponseEntity<>(restaurantService.deleteRestaurant(id), HttpStatus.OK);
    }

    @AllowAdmin
    @PutMapping("/{id}/schedule")
    public ResponseEntity<ScheduleResponse> createSchedule(
            @PathVariable Long id, @RequestBody ScheduleRequest scheduleRequest) {
        return new ResponseEntity<>(
                scheduleService.createSchedule(id, scheduleRequest), HttpStatus.CREATED);
    }

    @AllowAdmin
    @PostMapping("/{id}/schedule/{schedule_id}")
    public ResponseEntity<ScheduleResponse> editSchedule(
            @PathVariable Long id,
            @PathVariable Long schedule_id,
            @RequestBody ScheduleRequest scheduleRequest) {
        return new ResponseEntity<>(
                scheduleService.editSchedule(id, schedule_id, scheduleRequest), HttpStatus.OK);
    }

    @AllowAdmin
    @GetMapping("/{id}/user")
    public ResponseEntity<TimeRegisterGenericResponse<EmployeeResponse>> getUserOfRestaurant(
            @PathVariable Long id,
            @RequestParam(name = "startPage", defaultValue = Constants.DEFAULT_START_PAGE + "")
                    int startPage,
            @RequestParam(name = "pageSize", defaultValue = Constants.DEFAULT_PAGE_SIZE + "")
                    int pageSize) {
        return new ResponseEntity<>(
                restaurantService.getAllUserOfRestaurant(id, startPage, pageSize), HttpStatus.OK);
    }
}
