package com.snaacker.timeregister.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.snaacker.timeregister.persistent.Restaurant;
import com.snaacker.timeregister.persistent.RestaurantConfigureData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(
        value = {"hibernateLazyInitializer", "handler"},
        ignoreUnknown = true)
public class RestaurantDataDto implements Serializable {
    private Date timesheetClosingDate;

    public RestaurantDataDto(RestaurantConfigureData restaurantConfigureData){
        this.timesheetClosingDate = restaurantConfigureData.getTimesheetClosingDate();
    }
}
