package com.snaacker.timeregister.repository;

import com.snaacker.timeregister.persistent.Employee;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByAccountName(String username);

    Employee findByEmail(String email);

    //    Employee getByEmployeeId(String userName);

    @Query(
            value =
                    "SELECT * FROM users u JOIN user_restaurant ur ON ur.user_id = u.id WHERE"
                        + " ur.restaurant_id = :restaurantId AND ur.is_restaurant_manager = false",
            nativeQuery = true)
    Set<Employee> getAllEmployeeOfRestaurant(long restaurantId);
}
