package com.snaacker.timeregister.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.snaacker.timeregister.persistent.RestaurantConfigurationData;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(
        value = {"hibernateLazyInitializer", "handler"},
        ignoreUnknown = true)
public class RestaurantDataDto implements Serializable {
    private Date timesheetClosingDate;

    public RestaurantDataDto(RestaurantConfigurationData restaurantConfigurationData) {
        this.timesheetClosingDate = restaurantConfigurationData.getTimesheetClosingDate();
    }
}
