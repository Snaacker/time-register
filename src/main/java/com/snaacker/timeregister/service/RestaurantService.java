package com.snaacker.timeregister.service;

import com.snaacker.timeregister.exception.TimeRegisterObjectNotFoundException;
import com.snaacker.timeregister.model.RestaurantRequest;
import com.snaacker.timeregister.model.RestaurantResponse;
import com.snaacker.timeregister.model.TimeRegisterGenericResponse;
import com.snaacker.timeregister.persistent.Restaurant;
import com.snaacker.timeregister.repository.RestaurantRepository;
import com.snaacker.timeregister.utils.Constants;
import com.snaacker.timeregister.utils.DtoTransformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

  @Autowired private RestaurantRepository restaurantRepository;

  public TimeRegisterGenericResponse<RestaurantResponse> getAllRestaurant(int startingPage, int pageSize) {
    Pageable pageable = PageRequest.of(startingPage, pageSize);
    List<RestaurantResponse> listRestaurantResponse =
        restaurantRepository.findAll(pageable).stream()
            .map(RestaurantResponse::new)
            .collect(Collectors.toList());
    return new TimeRegisterGenericResponse<>(listRestaurantResponse, startingPage, pageSize);
  }

  public Page<Restaurant> createRestaurant(RestaurantRequest restaurantRequest) {
    Restaurant newRestaurant = new Restaurant();
    // TODO: convert dto to Restaurant Object
    restaurantRepository.save(newRestaurant);
    Pageable pageable = PageRequest.of(Constants.DEFAULT_START_PAGE, Constants.DEFAULT_PAGE_SIZE);
    return restaurantRepository.findAll(pageable);
  }

  public Restaurant editRestaurant(RestaurantRequest restaurantRequest) {
    Restaurant restaurant = new Restaurant();

    return restaurantRepository.save(restaurant);
  }

  public String deleteRestaurant(long id) throws TimeRegisterObjectNotFoundException {
    Restaurant restaurant =
        restaurantRepository
            .findById(id)
            .orElseThrow(() -> new TimeRegisterObjectNotFoundException("Restaurant not found"));
    restaurantRepository.delete(restaurant);
    return "OK";
  }

  public RestaurantResponse getRestaurantById(Long id) {
    Restaurant restaurant = restaurantRepository.getById(id);
    return new RestaurantResponse(restaurant);
  }
}
