package com.neulab.fund.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = com.example.background.BackgroundApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FundCompanyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllFundCompany() throws Exception {
        mockMvc.perform(get("/api/fund-companies"))
                .andExpect(status().isOk());
    }

    @Test
    void contextLoads() {
        assertTrue(true);
    }
} 