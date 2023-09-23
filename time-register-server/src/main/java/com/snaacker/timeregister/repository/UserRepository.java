package com.snaacker.timeregister.repository;

import com.snaacker.timeregister.persistent.User;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    User getByAccountId(String userName);

    @Query(
            value =
                    "SELECT * FROM users u JOIN user_restaurant ur ON ur.user_id = u.id WHERE"
                        + " ur.restaurant_id = :restaurantId AND ur.is_restaurant_manager = false",
            nativeQuery = true)
    Set<User> getAllEmployeeOfRestaurant(long restaurantId);
}
