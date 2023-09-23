package com.snaacker.timeregister.persistent;

import com.snaacker.timeregister.model.UserRestaurantDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

@Entity
@Table(name = "employee_restaurant")
@RestResource(exported = false)
@NoArgsConstructor
@Getter
@Setter
public class EmployeeRestaurant extends BaseObject implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "is_restaurant_manager")
    private boolean isRestaurantManager;

    public EmployeeRestaurant(UserRestaurantDto userRestaurantDto) {
        this.isRestaurantManager = userRestaurantDto.isManager();
    }
}
