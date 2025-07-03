package com.neulab.fund.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neulab.fund.entity.Fund;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = com.example.background.BackgroundApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FundControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllFunds() throws Exception {
        mockMvc.perform(get("/api/funds/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }

    @Test
    void testGetFundById() throws Exception {
        mockMvc.perform(get("/api/funds/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }

    @Test
    void testGetFundsWithFilter() throws Exception {
        mockMvc.perform(get("/api/funds")
                .param("keyword", "指数")
                .param("page", "0")
                .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }

    @Test
    void testCreateFund() throws Exception {
        Fund fund = new Fund();
        fund.setName("测试基金");
        fund.setCode("TEST123");
        String json = objectMapper.writeValueAsString(fund);
        mockMvc.perform(post("/api/funds")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }

    @Test
    void testUpdateFund() throws Exception {
        Fund fund = new Fund();
        fund.setName("更新基金");
        String json = objectMapper.writeValueAsString(fund);
        mockMvc.perform(put("/api/funds/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }

    @Test
    void testDeleteFund() throws Exception {
        mockMvc.perform(delete("/api/funds/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }

    @Test
    void testGetFundProfile() throws Exception {
        mockMvc.perform(get("/api/funds/{id}/profile", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }
} 