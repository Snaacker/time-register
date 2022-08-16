package com.snaacker.timeregister.persistent;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "restaurant")
@RestResource(exported = false)
public class Restaurant extends BaseObject{
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @OneToMany(mappedBy = "restaurant", cascade =  CascadeType.MERGE)
    private List<UserRestaurant> userRestaurant;
    @OneToMany(mappedBy = "restaurant", cascade =  CascadeType.MERGE)
    private List<RestaurantConfigureData> restaurantConfigureData;
    @OneToMany(mappedBy = "restaurant", cascade =  CascadeType.MERGE)
    private List<Schedule> schedule;
}
