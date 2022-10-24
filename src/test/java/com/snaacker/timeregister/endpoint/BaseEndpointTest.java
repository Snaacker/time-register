package com.snaacker.timeregister.endpoint;

import com.snaacker.timeregister.FixtureTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class BaseEndpointTest extends FixtureTest {
    static final String token = "";
    @Autowired protected static MockMvc mockMvc;

    //    @BeforeAll
    //    public static void setup() throws Exception {
    //
    // mockMvc.perform(get("/api/v1/authentication")).andDo(print()).andExpect(status().isOk())
    //                .andExpect(content().string(containsString("OK")));
    //    }
}
