package com.snaacker.timeregister.persistent;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "restaurant_configure_data")
public class RestaurantConfigureData extends BaseObject{
    @Column(name = "timesheet_closing_date", unique = true)
    private Date timesheetClosingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;
}
