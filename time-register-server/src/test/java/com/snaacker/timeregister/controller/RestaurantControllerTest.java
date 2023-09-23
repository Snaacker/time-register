package com.snaacker.timeregister.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.snaacker.timeregister.FixtureTest;
import com.snaacker.timeregister.exception.TimeRegisterBadRequestException;
import com.snaacker.timeregister.exception.TimeRegisterObjectNotFoundException;
import com.snaacker.timeregister.model.request.RestaurantRequest;
import com.snaacker.timeregister.model.response.RestaurantResponse;
import com.snaacker.timeregister.model.response.TimeRegisterGenericResponse;
import com.snaacker.timeregister.service.RestaurantService;
import com.snaacker.timeregister.service.ScheduleService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestaurantControllerTest extends FixtureTest {

    RestaurantController restaurantController;
    RestaurantService restaurantService;
    ScheduleService scheduleService;

    @BeforeEach
    public void setup() {
        restaurantService = mock(RestaurantService.class);
        scheduleService = mock(ScheduleService.class);
        restaurantController = new RestaurantController(restaurantService, scheduleService);
    }

    @Test
    public void getAllRestaurantWithValidValueShouldReturnListRestaurant() {
        List<RestaurantResponse> restaurantResponseList = new ArrayList<>();
        restaurantResponseList.add(
                new RestaurantResponse("restaurant1", "Hanoi", "email1", "123456"));
        restaurantResponseList.add(
                new RestaurantResponse("restaurant2", "Saigon", "email2", "123456"));
        restaurantResponseList.add(
                new RestaurantResponse("restaurant3", "Danang", "email3", "123456"));
        TimeRegisterGenericResponse<RestaurantResponse> timeRegisterGenericResponse =
                new TimeRegisterGenericResponse<>(restaurantResponseList, 20, 1);
        when(restaurantService.getAllRestaurant(anyInt(), anyInt()))
                .thenReturn(timeRegisterGenericResponse);

        ResponseEntity<TimeRegisterGenericResponse<RestaurantResponse>> responseResponseEntity =
                restaurantController.getListRestaurant(1, 20);
        assertThat(responseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseResponseEntity.getBody().getTotal()).isEqualTo(3);
    }

    @Test
    public void getRestaurantByIdWithValidValueShouldReturnCorrectObject() {
        RestaurantResponse restaurantResponse =
                new RestaurantResponse("Restaurant1", "Hanoi", "email1", "123456");
        when(restaurantService.getRestaurantById(anyLong())).thenReturn(restaurantResponse);
        ResponseEntity<RestaurantResponse> returnObject =
                restaurantController.getRestaurantById(1L);
        assertThat(returnObject.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(returnObject.getBody().getName()).isEqualTo("Restaurant1");
        assertThat(returnObject.getBody().getAddress()).isEqualTo("Hanoi");
        assertThat(returnObject.getBody().getEmail()).isEqualTo("email1");
        assertThat(returnObject.getBody().getPhoneNumber()).isEqualTo("123456");
    }

    @Test
    public void getRestaurantByIdWithInvalidIDValueShouldReturnBadRequest() {
        when(restaurantService.getRestaurantById(anyLong()))
                .thenThrow(new TimeRegisterObjectNotFoundException("Can not find object"));
        Exception exception =
                assertThrows(
                        RuntimeException.class,
                        () -> {
                            restaurantController.getRestaurantById(1L);
                        });
        assertThat(exception.getMessage()).isEqualTo("Can not find object");
        assertThat(exception.getClass()).isEqualTo(TimeRegisterObjectNotFoundException.class);
    }

    @Test
    public void createRestaurantWithValidValueShouldReturnSuccess() {
        RestaurantResponse restaurantResponse =
                new RestaurantResponse("Restaurant1", "Hanoi", "email1", "123456");
        when(restaurantService.createRestaurant(any())).thenReturn(restaurantResponse);
        ResponseEntity<RestaurantResponse> returnObject =
                restaurantController.createRestaurant(new RestaurantRequest());
        RestaurantResponse returnRestaurantObject = returnObject.getBody();
        assertThat(returnObject.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(returnRestaurantObject.getName()).isEqualTo("Restaurant1");
        assertThat(returnRestaurantObject.getAddress()).isEqualTo("Hanoi");
        assertThat(returnRestaurantObject.getEmail()).isEqualTo("email1");
        assertThat(returnRestaurantObject.getPhoneNumber()).isEqualTo("123456");
    }

    @Test
    public void createRestaurantWithInvalidValueShouldReturnBadRequest() {
        when(restaurantService.createRestaurant(any()))
                .thenThrow(new TimeRegisterBadRequestException("Can not save object"));
        Exception exception =
                assertThrows(
                        RuntimeException.class,
                        () -> {
                            restaurantController.createRestaurant(new RestaurantRequest());
                        });
        assertThat(exception.getMessage()).isEqualTo("Can not save object");
        assertThat(exception.getClass()).isEqualTo(TimeRegisterBadRequestException.class);
    }

    @Test
    public void deleteRestaurantWithValidValueShouldReturnSuccess() {
        when(restaurantService.deleteRestaurant(anyLong())).thenReturn("OK");
        ResponseEntity<String> returnEntity = restaurantController.deleteRestaurant(1l);
        assertThat(returnEntity.getBody()).isEqualTo("OK");
    }

    @Test
    public void deleteNonExistRestaurantShouldReturnObjectNotFound() {
        when(restaurantService.deleteRestaurant(anyLong()))
                .thenThrow(new TimeRegisterObjectNotFoundException("Restaurant not found"));
        Exception exception =
                assertThrows(
                        RuntimeException.class,
                        () -> {
                            restaurantController.deleteRestaurant(1l);
                        });
        assertThat(exception.getMessage()).isEqualTo("Restaurant not found");
        assertThat(exception.getClass()).isEqualTo(TimeRegisterObjectNotFoundException.class);
    }
}
