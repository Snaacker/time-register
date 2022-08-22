package com.snaacker.timeregister.persistent;

import com.snaacker.timeregister.model.UserRestaurantDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_restaurant")
@IdClass(UserRestaurantId.class)
@RestResource(exported = false)
@NoArgsConstructor
@Getter
@Setter
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

    public UserRestaurant(UserRestaurantDto userRestaurantDto){
        this.isRestaurantManager = userRestaurantDto.isManager();
    }
}
