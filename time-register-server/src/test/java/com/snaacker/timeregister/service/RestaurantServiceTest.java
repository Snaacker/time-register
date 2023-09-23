package com.snaacker.timeregister.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.snaacker.timeregister.FixtureTest;
import com.snaacker.timeregister.model.response.RestaurantResponse;
import com.snaacker.timeregister.model.response.TimeRegisterGenericResponse;
import com.snaacker.timeregister.persistent.Restaurant;
import com.snaacker.timeregister.repository.RestaurantRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class RestaurantServiceTest extends FixtureTest {
    static RestaurantRepository restaurantRepository;

    static RestaurantService restaurantService;

    @BeforeAll
    public static void Setup() {
        restaurantRepository = mock(RestaurantRepository.class);
        restaurantService = new RestaurantService(restaurantRepository);
    }

    @Test
    public void getRestaurantWithValidValueShouldReturnListObject() {
        List<Restaurant> listRestaurant = new ArrayList<>();
        listRestaurant.add(new Restaurant());
        listRestaurant.add(new Restaurant());
        // TODO: move magic number out of this
        Pageable pageable = PageRequest.of(OFFSET, PAGE_SIZE);
        PageImpl<Restaurant> pageRestaurant = new PageImpl<>(listRestaurant, pageable, PAGE_SIZE);
        when(restaurantRepository.findAll((Pageable) any())).thenReturn(pageRestaurant);
        TimeRegisterGenericResponse<RestaurantResponse> timeRegisterGenericResponse =
                restaurantService.getAllRestaurant(OFFSET, PAGE_SIZE);
        assertThat(timeRegisterGenericResponse.getTotal()).isEqualTo(2);
    }
}
