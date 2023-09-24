package com.snaacker.timeregister.endpoint;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class HealthCheckEndpointTest extends BaseEndpointTest {

    @Test
    public void testHealthCheckShouldReturnSuccess() throws Exception {
        mockMvc.perform(get("/api/v1/health_check"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK")));
    }
}
