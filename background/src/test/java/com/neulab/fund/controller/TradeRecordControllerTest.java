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
class TradeRecordControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertTrue(true);
    }

    @Test
    void testGetAllRecords() throws Exception {
        mockMvc.perform(get("/api/trade-records"))
            .andExpect(status().isOk());
    }

    @Test
    void testGetRecordById() throws Exception {
        mockMvc.perform(get("/api/trade-records/1"))
            .andExpect(status().isOk());
    }

    @Test
    void testCreateRecord() throws Exception {
        String json = "{}";
        mockMvc.perform(post("/api/trade-records")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk());
    }
} 