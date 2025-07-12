package com.neulab.fund.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neulab.fund.entity.Fund;
import com.neulab.fund.service.FundService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = com.example.background.BackgroundApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FundControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllFunds() throws Exception {
        mockMvc.perform(get("/api/funds"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFundById() throws Exception {
        mockMvc.perform(get("/api/funds/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFundsWithFilter() throws Exception {
        mockMvc.perform(get("/api/funds")
                .param("keyword", "test")
                .param("companyId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateFund() throws Exception {
        mockMvc.perform(post("/api/funds")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateFund() throws Exception {
        mockMvc.perform(put("/api/funds/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteFund() throws Exception {
        mockMvc.perform(delete("/api/funds/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFundProfile() throws Exception {
        mockMvc.perform(get("/api/funds/1/profile"))
                .andExpect(status().isOk());
    }
} 