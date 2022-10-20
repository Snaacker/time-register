package com.snaacker.timeregister.endpoint;

import com.snaacker.timeregister.controller.CheckHealthController;
import com.snaacker.timeregister.controller.UserController;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest(CheckHealthController.class)

public class HealthCheckEndpointTest {

    @Autowired
    protected MockMvc mockMvc;
//    @Disabled("Enable after correct this")
//    @Test
//    public void testHealthCheckShouldReturnSuccess() throws Exception {
//
//        mockMvc.perform(get("/api/v1/health_check")).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString("OK")));
//    }
}
