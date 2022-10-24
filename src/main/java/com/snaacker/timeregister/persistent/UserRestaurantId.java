package com.snaacker.timeregister.persistent;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

@Getter
@Setter
@RestResource(exported = false)
public class UserRestaurantId implements Serializable {
    private int users;
    private int restaurant;
}
