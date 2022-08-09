package com.snaacker.timeregister.service;

import com.snaacker.timeregister.exception.TimeRegisterObjectNotFoundException;
import com.snaacker.timeregister.model.RestaurantRequest;
import com.snaacker.timeregister.persistent.Restaurant;
import com.snaacker.timeregister.repository.RestaurantRepository;
import com.snaacker.timeregister.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

  @Autowired private RestaurantRepository restaurantRepository;

  public Page<Restaurant> getAllRestaurant(int startingPage, int pageSize) {
    Pageable pageable = PageRequest.of(startingPage, pageSize);
    return restaurantRepository.findAll(pageable);
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
}
