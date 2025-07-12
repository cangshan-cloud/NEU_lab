package com.neulab.fund.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.http.MediaType;

@SpringBootTest(classes = com.example.background.BackgroundApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FundPortfolioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertTrue(true);
    }

    @Test
    void testGetAllPortfolios() throws Exception {
        mockMvc.perform(get("/api/fund-portfolios"))
            .andExpect(status().isOk());
    }

    @Test
    void testGetPortfolioById() throws Exception {
        mockMvc.perform(get("/api/fund-portfolios/1"))
            .andExpect(status().isOk());
    }

    @Test
    void testCreatePortfolio() throws Exception {
        String json = "{}";
        mockMvc.perform(post("/api/fund-portfolios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk());
    }
} 