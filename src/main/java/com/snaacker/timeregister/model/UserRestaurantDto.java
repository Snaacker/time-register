package com.snaacker.timeregister.model;

import com.snaacker.timeregister.model.request.RestaurantRequest;
import com.snaacker.timeregister.model.request.UserRequest;
import com.snaacker.timeregister.persistent.UserRestaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRestaurantDto {
    private UserRequest userRequest;
    private RestaurantRequest restaurantRequest;
    private boolean isManager;

    public UserRestaurantDto(UserRestaurant userRestaurant) {
        this.isManager = userRestaurant.isRestaurantManager();
    }
}
