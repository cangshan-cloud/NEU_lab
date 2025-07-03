package com.neulab.fund.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = com.example.background.BackgroundApplication.class)
@ActiveProfiles("test")
class FactorTreeRepositoryTest {

    @Test
    void testContextLoads() {
        assertTrue(true);
    }
} 