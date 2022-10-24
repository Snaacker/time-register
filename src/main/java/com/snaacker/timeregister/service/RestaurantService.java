package com.snaacker.timeregister.service;

import com.snaacker.timeregister.exception.TimeRegisterBadRequestException;
import com.snaacker.timeregister.exception.TimeRegisterObjectNotFoundException;
import com.snaacker.timeregister.model.*;
import com.snaacker.timeregister.model.request.RestaurantRequest;
import com.snaacker.timeregister.model.response.RestaurantResponse;
import com.snaacker.timeregister.model.response.TimeRegisterGenericResponse;
import com.snaacker.timeregister.persistent.Restaurant;
import com.snaacker.timeregister.persistent.RestaurantConfigureData;
import com.snaacker.timeregister.persistent.User;
import com.snaacker.timeregister.persistent.UserRestaurant;
import com.snaacker.timeregister.repository.RestaurantRepository;
import com.snaacker.timeregister.repository.UserRepository;
import com.snaacker.timeregister.utils.DtoTransformation;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    @Autowired private RestaurantRepository restaurantRepository;
    @Autowired private UserRepository userRepository;

    @Autowired
    public RestaurantService(final RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public TimeRegisterGenericResponse<RestaurantResponse> getAllRestaurant(
            int startingPage, int pageSize) {
        Pageable pageable = PageRequest.of(startingPage, pageSize);
        List<RestaurantResponse> listRestaurantResponse =
                restaurantRepository.findAll(pageable).stream()
                        .map(RestaurantResponse::new)
                        .collect(Collectors.toList());
        return new TimeRegisterGenericResponse<>(listRestaurantResponse, startingPage, pageSize);
    }

    public RestaurantResponse createRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant newRestaurant;
        try {
            newRestaurant = DtoTransformation.restaurantRequest2Restaurant(restaurantRequest);
        } catch (IllegalArgumentException e) {
            throw new TimeRegisterBadRequestException(e.getMessage());
        }
        // TODO: convert dto to Restaurant Object + check email format
        return new RestaurantResponse(restaurantRepository.save(newRestaurant));
    }

    public RestaurantResponse editRestaurant(long id, RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRepository.getById(id);
        if (null == restaurant) {
            throw new TimeRegisterBadRequestException("Restaurant does not exit");
        }
        if (null != restaurantRequest.getAddress()) {
            restaurant.setAddress(restaurantRequest.getAddress());
        }
        if (null != restaurantRequest.getName()) {
            restaurant.setName(restaurantRequest.getName());
        }
        if (null != restaurantRequest.getRestaurantDataDto()) {
            restaurant.setRestaurantConfigureData(
                    restaurantRequest.getRestaurantDataDto().stream()
                            .map(RestaurantConfigureData::new)
                            .collect(Collectors.toSet()));
        }
        if (null != restaurantRequest.getUserRestaurantDto()) {
            restaurant.setUserRestaurant(
                    restaurantRequest.getUserRestaurantDto().stream()
                            .map(this::userRestaurantDto2userRestaurant)
                            .collect(Collectors.toSet()));
        }

        return new RestaurantResponse(restaurantRepository.save(restaurant));
    }

    private UserRestaurant userRestaurantDto2userRestaurant(UserRestaurantDto userRestaurantDto) {
        UserRestaurant userRestaurant = new UserRestaurant(userRestaurantDto);
        userRestaurant.setUsers(new User(userRestaurantDto.getUserRequest()));
        userRestaurant.setRestaurant(new Restaurant(userRestaurantDto.getRestaurantRequest()));
        return userRestaurant;
    }

    public String deleteRestaurant(long id) throws TimeRegisterObjectNotFoundException {
        Restaurant restaurant =
                restaurantRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new TimeRegisterObjectNotFoundException(
                                                "Restaurant not found"));
        restaurantRepository.delete(restaurant);
        return "OK";
    }

    public RestaurantResponse getRestaurantById(Long id) {
        Restaurant restaurant;
        try {
            restaurant = restaurantRepository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new TimeRegisterObjectNotFoundException("Can not find object" + e.getMessage());
        }
        return new RestaurantResponse(restaurant);
    }
}
