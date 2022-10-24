package com.snaacker.timeregister.persistent;

import com.snaacker.timeregister.model.request.RestaurantRequest;
import java.util.HashSet;
import java.util.Set;
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
    private Set<UserRestaurant> userRestaurant = new HashSet<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.MERGE)
    private Set<RestaurantConfigureData> restaurantConfigureData;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.MERGE)
    private Set<Schedule> schedule;

    public Restaurant(RestaurantRequest restaurantRequest) {
        this.name = restaurantRequest.getName();
        this.address = restaurantRequest.getAddress();
        setCreatedDate();
        setUpdatedDate();
    }

    public Restaurant() {
        super();
        setCreatedDate();
        setUpdatedDate();
    }
}
