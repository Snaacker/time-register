package com.snaacker.timeregister.service;

import com.snaacker.timeregister.exception.TimeRegisterBadRequestException;
import com.snaacker.timeregister.exception.TimeRegisterObjectNotFoundException;
import com.snaacker.timeregister.model.UserRestaurantDto;
import com.snaacker.timeregister.model.request.RestaurantRequest;
import com.snaacker.timeregister.model.response.EmployeeResponse;
import com.snaacker.timeregister.model.response.RestaurantResponse;
import com.snaacker.timeregister.model.response.TimeRegisterGenericResponse;
import com.snaacker.timeregister.persistent.Employee;
import com.snaacker.timeregister.persistent.EmployeeRestaurant;
import com.snaacker.timeregister.persistent.Restaurant;
import com.snaacker.timeregister.repository.EmployeeRepository;
import com.snaacker.timeregister.repository.RestaurantRepository;
import com.snaacker.timeregister.utils.DtoTransformation;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    @Autowired private RestaurantRepository restaurantRepository;
    @Autowired private EmployeeRepository employeeRepository;

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
        if (null != restaurantRequest.getUserRestaurantDto()) {
            restaurant.setEmployeeRestaurant(
                    restaurantRequest.getUserRestaurantDto().stream()
                            .map(this::userRestaurantDto2userRestaurant)
                            .collect(Collectors.toSet()));
        }

        if (null != restaurantRequest.getManager()) {
            Employee restaurantManager =
                    employeeRepository.findByAccountName(restaurantRequest.getManager());
            // TODO: process user not found
            restaurant.setManager(restaurantManager);
        }

        if (null != restaurantRequest.getEmail()) {
            restaurant.setEmail(restaurantRequest.getEmail());
        }

        if (null != restaurantRequest.getPhoneNumber()) {
            restaurant.setPhoneNumber(restaurantRequest.getPhoneNumber());
        }
        return new RestaurantResponse(restaurantRepository.save(restaurant));
    }

    private EmployeeRestaurant userRestaurantDto2userRestaurant(
            UserRestaurantDto userRestaurantDto) {
        EmployeeRestaurant employeeRestaurant = new EmployeeRestaurant(userRestaurantDto);
        employeeRestaurant.setEmployee(new Employee(userRestaurantDto.getEmployeeRequest()));
        employeeRestaurant.setRestaurant(new Restaurant(userRestaurantDto.getRestaurantRequest()));
        return employeeRestaurant;
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

    public TimeRegisterGenericResponse<EmployeeResponse> getAllUserOfRestaurant(
            long restaurantId, int startPage, int pageSize) {
        Set<Employee> employeeSet = employeeRepository.getAllEmployeeOfRestaurant(restaurantId);
        List<EmployeeResponse> employeeRespons =
                employeeSet.stream().map(EmployeeResponse::new).collect(Collectors.toList());
        return new TimeRegisterGenericResponse<>(employeeRespons, startPage, pageSize);
    }
}
