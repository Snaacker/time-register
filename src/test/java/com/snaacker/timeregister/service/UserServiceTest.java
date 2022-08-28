package com.snaacker.timeregister.service;

import com.snaacker.timeregister.FixtureTest;
import com.snaacker.timeregister.persistent.User;
import com.snaacker.timeregister.repository.RestaurantRepository;
import com.snaacker.timeregister.repository.TimesheetRecordRepository;
import com.snaacker.timeregister.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest extends FixtureTest {

  @Autowired UserService userService;

  UserRepository userRepository;
  TimesheetRecordRepository timesheetRecordRepository;

  @BeforeEach
  public void Setup() {
    userRepository = mock(UserRepository.class);
    timesheetRecordRepository = mock(TimesheetRecordRepository.class);
    userService = new UserService(userRepository, timesheetRecordRepository);
  }

  @Test
  public void getUserWithValidParametersShouldReturnSuccess() {
    List<User> listUser = Arrays.asList(new User(), new User(), new User());
    when(userRepository.findAll((Pageable) any())).thenReturn(new PageImpl<User>(listUser));
    assertThat(userService.getListUser(OFFSET, PAGE_SIZE).getTotal()).isEqualTo(3);
  }
}
