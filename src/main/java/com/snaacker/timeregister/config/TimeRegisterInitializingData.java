package com.snaacker.timeregister.config;

import com.snaacker.timeregister.persistent.Restaurant;
import com.snaacker.timeregister.persistent.Role;
import com.snaacker.timeregister.persistent.User;
import com.snaacker.timeregister.persistent.UserRestaurant;
import com.snaacker.timeregister.repository.RestaurantRepository;
import com.snaacker.timeregister.repository.UserRepository;
import com.snaacker.timeregister.utils.Utilities;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TimeRegisterInitializingData {

    @Autowired private UserRepository userRepository;
    @Autowired private RestaurantRepository restaurantRepository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) throws Exception {
        // TODO: load sample data using sql file - this will failed if ddl-auto is not `create`
        String password = Base64.getEncoder().encodeToString(Utilities.encryptToByte("qwerty"));
        System.out.println("================ Password: " + password);
        // Create first admin user - using flyway later
        User admin = new User();
        admin.setUsername("admin");
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setEmail("me@snaacker.com");
        admin.setAdmin(true);
        admin.setRoleName(Role.ADMIN);
        admin.setPassword(password);
        userRepository.save(admin);

        Restaurant restaurantTokyoSushi = new Restaurant();
        restaurantTokyoSushi.setName("Tokyo Sushi");
        restaurantTokyoSushi.setAddress("Berlin Germany");
        restaurantTokyoSushi.setPhoneNumber("123-456-789");
        restaurantRepository.save(restaurantTokyoSushi);

        User employee = new User();
        employee.setUsername("hoang_a_bi");
        employee.setRoleName(Role.EMPLOYEE);
        employee.setFirstName("Hoang");
        employee.setLastName("Duy");
        employee.setEmail("duy@magdeburg.de");
        employee.setPassword(password);

        UserRestaurant employeeRestaurant = new UserRestaurant();
        employeeRestaurant.setUsers(employee);
        employeeRestaurant.setRestaurant(restaurantTokyoSushi);
        Set<UserRestaurant> setUserRestaurant = new HashSet<>();
        setUserRestaurant.add(employeeRestaurant);
        employee.setUserRestaurants(setUserRestaurant);
        userRepository.save(employee);

        User manager = new User();
        manager.setUsername("snaacker");
        manager.setRoleName(Role.MANAGER);
        manager.setFirstName("Tran");
        manager.setLastName("Huy");
        manager.setEmail("you@snaacker.com");
        manager.setPassword(password);

        UserRestaurant managerRestaurant = new UserRestaurant();
        managerRestaurant.setUsers(manager);
        managerRestaurant.setRestaurant(restaurantTokyoSushi);
        managerRestaurant.setRestaurantManager(true);
        Set<UserRestaurant> setManagerRestaurant = new HashSet<>();
        setManagerRestaurant.add(managerRestaurant);
        manager.setUserRestaurants(setManagerRestaurant);
        userRepository.save(manager);

    }
}
