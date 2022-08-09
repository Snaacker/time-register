package com.snaacker.timeregister.persistent;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "restaurant")
public class Restaurant extends BaseObject{
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.MERGE)
    private List<User> users;
    @OneToMany(mappedBy = "restaurant", cascade =  CascadeType.MERGE)
    private List<RestaurantConfigureData> restaurantConfigureData;
}
