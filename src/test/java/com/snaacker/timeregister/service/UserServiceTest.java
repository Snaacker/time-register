package com.snaacker.timeregister.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.snaacker.timeregister.FixtureTest;
import com.snaacker.timeregister.model.TimeRegisterGenericResponse;
import com.snaacker.timeregister.model.UserResponse;
import com.snaacker.timeregister.persistent.Role;
import com.snaacker.timeregister.persistent.User;
import com.snaacker.timeregister.repository.TimesheetRecordRepository;
import com.snaacker.timeregister.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class UserServiceTest extends FixtureTest {

    @Autowired UserService userService;

    @MockBean static UserRepository userRepository;
    @MockBean static TimesheetRecordRepository timesheetRecordRepository;

    @Test
    public void getUserWithValidParametersShouldReturnSuccess() {
        List<User> listUser =
                Arrays.asList(
                        new User(
                                "username1",
                                "password1",
                                "accoundId1",
                                "firstName1",
                                "lastName1",
                                "email1",
                                "phoneNumber1",
                                "address1",
                                Role.EMPLOYEE),
                        new User(
                                "username2",
                                "password2",
                                "accountId2",
                                "firstName2",
                                "lastName2",
                                "email2",
                                "phoneNumber2",
                                "address2",
                                Role.EMPLOYEE),
                        new User(
                                "username3",
                                "password3",
                                "accountId3",
                                "firstName3",
                                "lastName3",
                                "email3",
                                "phoneNumber#",
                                "address3",
                                Role.EMPLOYEE));
        when(userRepository.findAll((Pageable) any())).thenReturn(new PageImpl<>(listUser));
        TimeRegisterGenericResponse<UserResponse> returnUsers =
                userService.getListUser(OFFSET, PAGE_SIZE);
        assertThat(returnUsers.getTotal()).isEqualTo(3);
        assertThat(returnUsers.getOffset()).isEqualTo(OFFSET);
        assertThat(returnUsers.getPageSize()).isEqualTo(PAGE_SIZE);
        assertThat(
                        returnUsers.getGenericObject().stream()
                                .anyMatch(x -> x.getUsername().equals("username1")))
                .isTrue();
    }
}
