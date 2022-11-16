package com.snaacker.timeregister.persistent;

import com.snaacker.timeregister.model.RestaurantDataDto;
import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

@Entity
@Getter
@Setter
@Table(name = "restaurant_configuration_data")
@NoArgsConstructor
@RestResource(exported = false)
public class RestaurantConfigurationData extends BaseObject {
    @Column(name = "timesheet_closing_date", unique = true)
    private Date timesheetClosingDate;

    @Column(name = "opening_hour")
    private int openingHour;

    @Column(name = "closing_hour")
    private int closingHour;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    public RestaurantConfigurationData(RestaurantDataDto restaurantDataDto) {
        this.timesheetClosingDate = restaurantDataDto.getTimesheetClosingDate();
    }
}
