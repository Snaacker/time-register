package com.snaacker.timeregister.controller;

import com.snaacker.timeregister.FixtureTest;
import com.snaacker.timeregister.model.RestaurantResponse;
import com.snaacker.timeregister.model.TimeRegisterGenericResponse;
import com.snaacker.timeregister.service.RestaurantService;
import com.snaacker.timeregister.service.ScheduleService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestaurantControllerTest extends FixtureTest {

  @Autowired
  RestaurantController restaurantController;

  RestaurantService restaurantService;
  ScheduleService scheduleService;

  @BeforeEach
  void setup() {
    restaurantService = mock(RestaurantService.class);
    scheduleService = mock(ScheduleService.class);
    restaurantController = new RestaurantController(restaurantService, scheduleService);
  }

  @Test
  public void getAllRestaurantWithValidValueShouldReturnListRestaurant() {
    List<RestaurantResponse> restaurantResponseList = new ArrayList<>();
    restaurantResponseList.add(new RestaurantResponse("restaurant1", "Hanoi"));
    restaurantResponseList.add(new RestaurantResponse("restaurant2", "Saigon"));
    restaurantResponseList.add(new RestaurantResponse("restaurant3", "Danang"));
    TimeRegisterGenericResponse<RestaurantResponse> timeRegisterGenericResponse =
        new TimeRegisterGenericResponse<>(restaurantResponseList, 20, 1);
    when(restaurantService.getAllRestaurant(anyInt(), anyInt()))
        .thenReturn(timeRegisterGenericResponse);

    ResponseEntity<TimeRegisterGenericResponse<RestaurantResponse>> responseResponseEntity =
        restaurantController.getListRestaurant(1, 20);
    assertThat(responseResponseEntity.getBody().getTotal()).isEqualTo(3);
  }
}
