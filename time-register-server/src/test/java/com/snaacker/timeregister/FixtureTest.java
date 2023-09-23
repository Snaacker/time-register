package com.snaacker.timeregister;

import org.springframework.boot.test.context.SpringBootTest;

// @ExtendWith(SpringExtension.class)
// @ExtendWith(MockitoExtension.class)
// @WebAppConfiguration
@SpringBootTest
public abstract class FixtureTest {
    public static int OFFSET = 1;
    public static int PAGE_SIZE = 20;
}
