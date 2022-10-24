package com.snaacker.timeregister.persistent;

import com.snaacker.timeregister.model.RestaurantRequest;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.MERGE)
    private List<UserRestaurant> userRestaurant;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.MERGE)
    private List<RestaurantConfigureData> restaurantConfigureData;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.MERGE)
    private List<Schedule> schedule;

    public Restaurant(RestaurantRequest restaurantRequest) {
        this.name = restaurantRequest.getName();
        this.address = restaurantRequest.getAddress();
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }

    public Restaurant() {
        super();
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }
}
