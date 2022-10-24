package com.snaacker.timeregister.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(
        value = {"hibernateLazyInitializer", "handler"},
        ignoreUnknown = true)
public class RestaurantRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private String address;

    @JsonProperty("user_restaurant")
    private List<UserRestaurantDto> userRestaurantDto;

    @JsonProperty("restaurant_data")
    private List<RestaurantDataDto> restaurantDataDto;
}
