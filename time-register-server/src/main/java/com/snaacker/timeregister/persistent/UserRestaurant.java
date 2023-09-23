package com.snaacker.timeregister.persistent;

import com.snaacker.timeregister.model.UserRestaurantDto;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

@Entity
@Table(name = "user_restaurant")
@RestResource(exported = false)
@NoArgsConstructor
@Getter
@Setter
public class UserRestaurant extends BaseObject implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User users;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "is_restaurant_manager")
    private boolean isRestaurantManager;

    public UserRestaurant(UserRestaurantDto userRestaurantDto) {
        this.isRestaurantManager = userRestaurantDto.isManager();
    }
}
