package com.snaacker.timeregister.config;

import com.snaacker.timeregister.persistent.Employee;
import com.snaacker.timeregister.persistent.EmployeeRestaurant;
import com.snaacker.timeregister.persistent.Restaurant;
import com.snaacker.timeregister.persistent.Role;
import com.snaacker.timeregister.repository.EmployeeRepository;
import com.snaacker.timeregister.repository.RestaurantRepository;
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

    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private RestaurantRepository restaurantRepository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) throws Exception {
        // TODO: load sample data using sql file - this will failed if ddl-auto is not `create`
        String password = Base64.getEncoder().encodeToString(Utilities.encryptToByte("qwerty"));
        System.out.println("================ Password: " + password);
        // Create first admin user - using flyway later
        Employee admin = new Employee();
        admin.setAccountName("admin");
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setAccountId("admin");
        admin.setEmail("me@snaacker.com");
        admin.setAdmin(true);
        admin.setRoleName(Role.ADMIN);
        admin.setPassword(password);
        employeeRepository.save(admin);

        Restaurant restaurantTokyoSushi = new Restaurant();
        restaurantTokyoSushi.setName("Tokyo Sushi");
        restaurantTokyoSushi.setAddress("Berlin Germany");
        restaurantTokyoSushi.setPhoneNumber("123-456-789");
        restaurantRepository.save(restaurantTokyoSushi);

        Employee employee = new Employee();
        employee.setAccountId("duyh");
        employee.setAccountName("hoang_a_bi");
        employee.setRoleName(Role.EMPLOYEE);
        employee.setFirstName("Hoang");
        employee.setLastName("Duy");
        employee.setEmail("duy@magdeburg.de");
        employee.setPassword(password);

        EmployeeRestaurant employeeRestaurant = new EmployeeRestaurant();
        employeeRestaurant.setEmployee(employee);
        employeeRestaurant.setRestaurant(restaurantTokyoSushi);
        Set<EmployeeRestaurant> setEmployeeRestaurant = new HashSet<>();
        setEmployeeRestaurant.add(employeeRestaurant);
        employee.setEmployeeRestaurants(setEmployeeRestaurant);
        employeeRepository.save(employee);

        Employee manager = new Employee();
        manager.setAccountId("huytq");
        manager.setAccountName("snaacker");
        manager.setRoleName(Role.MANAGER);
        manager.setFirstName("Tran");
        manager.setLastName("Huy");
        manager.setEmail("you@snaacker.com");
        manager.setPassword(password);

        EmployeeRestaurant managerRestaurant = new EmployeeRestaurant();
        managerRestaurant.setEmployee(manager);
        managerRestaurant.setRestaurant(restaurantTokyoSushi);
        managerRestaurant.setRestaurantManager(true);
        Set<EmployeeRestaurant> setManagerRestaurant = new HashSet<>();
        setManagerRestaurant.add(managerRestaurant);
        manager.setEmployeeRestaurants(setManagerRestaurant);
        employeeRepository.save(manager);
    }
}
