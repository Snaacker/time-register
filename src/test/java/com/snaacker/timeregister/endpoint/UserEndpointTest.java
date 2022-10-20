package com.snaacker.timeregister.endpoint;

import com.snaacker.timeregister.controller.UserController;
import com.snaacker.timeregister.model.UserResponse;
import com.snaacker.timeregister.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserEndpointTest {

  @Autowired
  protected MockMvc mockMvc;
  @MockBean
  private UserService userService;

//  @Disabled("Enabled after correct")
//  @Test
//  public void testCreateUserShouldSuccessfully() throws Exception {
//    UserResponse userResponse = new UserResponse();
//    userResponse.setUsername("TestUser");
//    when(userService.createUser(any())).thenReturn(userResponse);
//    mockMvc
//        .perform(put("/api/v1/user"))
//        .andDo(print())
//        .andExpect(status().isOk())
//            .andExpect(jsonPath("$", Matchers.hasSize(1)));
////        .andExpect(content().string(contains("TestUser")));
//  }
}
