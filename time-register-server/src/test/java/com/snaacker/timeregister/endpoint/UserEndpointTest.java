package com.snaacker.timeregister.endpoint;

import com.snaacker.timeregister.controller.UserController;
import com.snaacker.timeregister.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserEndpointTest {

    @Autowired protected MockMvc mockMvc;
    @MockBean private UserService userService;

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
