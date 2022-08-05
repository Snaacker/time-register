package com.snaacker.timeregister.config;

import com.snaacker.timeregister.persistent.Role;
import com.snaacker.timeregister.persistent.User;
import com.snaacker.timeregister.repository.UserRepository;
import com.snaacker.timeregister.utils.JwtTokenUtil;
import com.snaacker.timeregister.utils.Utilities;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimeRegisterInitializingBean implements InitializingBean {

  @Autowired private UserRepository userRepository;

  public void afterPropertiesSet() throws Exception {
    // Create first admin user - using flyway later
    User admin = new User();
    admin.setUsername("admin");
    admin.setAdmin(true);
    admin.setRoleName(Role.ADMIN);
    admin.setPassword(Utilities.encrypt("qwerty"));
    // TODO: for test
    userRepository.save(admin);
    JwtTokenUtil jwtTokenUtil = new JwtTokenUtil("abcd1234");
  }
}
