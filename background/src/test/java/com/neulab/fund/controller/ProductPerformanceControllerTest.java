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
class ProductPerformanceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertTrue(true);
    }

    @Test
    void testGetAllPerformances() throws Exception {
        mockMvc.perform(get("/api/product-performances"))
            .andExpect(status().isOk());
    }

    @Test
    void testGetPerformanceById() throws Exception {
        mockMvc.perform(get("/api/product-performances/1"))
            .andExpect(status().isOk());
    }

    @Test
    void testCreatePerformance() throws Exception {
        String json = "{}";
        mockMvc.perform(post("/api/product-performances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk());
    }
} 