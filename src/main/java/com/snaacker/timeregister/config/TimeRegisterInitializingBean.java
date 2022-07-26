package com.snaacker.timeregister.config;

import com.snaacker.timeregister.persistent.Role;
import com.snaacker.timeregister.persistent.User;
import com.snaacker.timeregister.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimeRegisterInitializingBean {

  @Autowired private UserRepository userRepository;

  public void afterPropertiesSet() throws Exception {
    // Create first admin user - using flyway later
    User admin = new User();
    admin.setUsername("admin");
    admin.setAdmin(true);
    admin.setRoleName(Role.ADMIN);
    // TODO: encode password here
    //        admin.setPassword("qwerty");
    userRepository.save(admin);
  }
}
