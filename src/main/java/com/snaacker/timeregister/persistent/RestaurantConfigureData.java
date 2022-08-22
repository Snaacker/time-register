package com.snaacker.timeregister.persistent;

import com.snaacker.timeregister.model.RestaurantDataDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "restaurant_configure_data")
@NoArgsConstructor
@RestResource(exported = false)
public class RestaurantConfigureData extends BaseObject{
    @Column(name = "timesheet_closing_date", unique = true)
    private Date timesheetClosingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    public RestaurantConfigureData(RestaurantDataDto restaurantDataDto){
        this.timesheetClosingDate = restaurantDataDto.getTimesheetClosingDate();
    }
}
