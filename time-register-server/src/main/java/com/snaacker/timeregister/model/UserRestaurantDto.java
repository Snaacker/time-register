package com.snaacker.timeregister.model;

import com.snaacker.timeregister.model.request.EmployeeRequest;
import com.snaacker.timeregister.model.request.RestaurantRequest;
import com.snaacker.timeregister.persistent.EmployeeRestaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRestaurantDto {
    private EmployeeRequest employeeRequest;
    private RestaurantRequest restaurantRequest;
    private boolean isManager;

    public UserRestaurantDto(EmployeeRestaurant employeeRestaurant) {
        this.isManager = employeeRestaurant.isRestaurantManager();
    }
}
