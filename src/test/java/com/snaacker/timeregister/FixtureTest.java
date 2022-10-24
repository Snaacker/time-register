package com.snaacker.timeregister;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

// @ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
@SpringBootTest
public abstract class FixtureTest {
    public static int OFFSET = 1;
    public static int PAGE_SIZE = 20;
}
