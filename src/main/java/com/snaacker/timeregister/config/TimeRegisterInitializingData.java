package com.snaacker.timeregister.config;

import com.snaacker.timeregister.persistent.Restaurant;
import com.snaacker.timeregister.persistent.Role;
import com.snaacker.timeregister.persistent.User;
import com.snaacker.timeregister.repository.RestaurantRepository;
import com.snaacker.timeregister.repository.UserRepository;
import com.snaacker.timeregister.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Base64;

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

    User manager = new User();
    manager.setUsername("snaacker");
    manager.setRoleName(Role.MANAGER);
    manager.setFirstName("Tran");
    manager.setLastName("Huy");
    manager.setEmail("you@snaacker.com");
    manager.setPassword(password);
    userRepository.save(manager);

    User employee = new User();
    employee.setUsername("hoang_a_bi");
    employee.setRoleName(Role.EMPLOYEE);
    employee.setFirstName("Hoang");
    employee.setLastName("Duy");
    employee.setEmail("duy@magdeburg.de");
    employee.setPassword(password);
    userRepository.save(employee);

    Restaurant restaurant = new Restaurant();
    restaurant.setName("Tokyo Sushi");
    restaurant.setAddress("Berlin Germany");
    restaurant.setPhoneNumber("123-456-789");
    restaurantRepository.save(restaurant);

  }
}
