package com.snaacker.timeregister.persistent;

import com.snaacker.timeregister.model.request.RestaurantRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

@Getter
@Setter
@Entity
@Table(name = "restaurant")
@RestResource(exported = false)
public class Restaurant extends BaseObject {
    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "status")
    private boolean status;

    //    @Column(name = "closing_date_type")
    //    private ClosingDateType closingDateType;

    @Column(name = "timesheet_closing_day_of_week")
    private DayOfWeek timesheetClosingDayOfWeek;

    @Column(name = "timesheet_closing_day_of_month")
    private int timesheetClosingDayOfMonth;

    @Column(name = "timesheet_closing_date")
    private Date timesheetClosingDate;

    @OneToOne private Employee manager;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.MERGE)
    private Set<EmployeeRestaurant> employeeRestaurant = new HashSet<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.MERGE)
    private Set<Schedule> schedule;

    public Restaurant(RestaurantRequest restaurantRequest) {
        this.name = restaurantRequest.getName();
        this.address = restaurantRequest.getAddress();
    }

    public Restaurant() {
        super();
    }
}
