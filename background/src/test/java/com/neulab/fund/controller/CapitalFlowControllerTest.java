package com.neulab.fund.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

cc@SpringBootTest(classes = com.example.background.BackgroundApplication.class)
@ActiveProfiles("test")
class CapitalFlowControllerTest {

    @Test
    void contextLoads() {
        assertTrue(true);
    }
} 