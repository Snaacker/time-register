package com.snaacker.timeregister.persistent;

import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_restaurant")
@IdClass(UserRestaurantId.class)
@RestResource(exported = false)
public class UserRestaurant implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User users;

    @Id
    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    @Column(name = "is_restaurant_manager")
    private boolean isRestaurantManager;
}
