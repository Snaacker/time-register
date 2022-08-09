package com.snaacker.timeregister.persistent;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "restaurant_configure_data")
@RestResource(exported = false)
public class RestaurantConfigureData extends BaseObject{
    @Column(name = "timesheet_closing_date", unique = true)
    private Date timesheetClosingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;
}
