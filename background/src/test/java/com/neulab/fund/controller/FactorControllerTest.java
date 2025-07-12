package com.neulab.fund.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neulab.fund.entity.Factor;
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
public class FactorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllFactors() throws Exception {
        mockMvc.perform(get("/api/factors"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetFactorById() throws Exception {
        mockMvc.perform(get("/api/factors/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetFactorByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/factors/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("因子不存在"));
    }

    @Test
    public void testCreateFactor() throws Exception {
        mockMvc.perform(post("/api/factors")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateFactor() throws Exception {
        Factor factor = new Factor();
        factor.setFactorCode("TEST001");
        factor.setFactorName("更新后的因子");
        factor.setFactorCategory("技术因子");
        factor.setFactorType("动量因子");
        factor.setDescription("更新后的描述");
        factor.setStatus("active");

        mockMvc.perform(put("/api/factors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(factor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void testUpdateFactorNotFound() throws Exception {
        Factor factor = new Factor();
        factor.setFactorCode("TEST001");
        factor.setFactorName("更新后的因子");

        mockMvc.perform(put("/api/factors/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(factor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("因子不存在"));
    }

    @Test
    public void testDeleteFactor() throws Exception {
        mockMvc.perform(delete("/api/factors/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteFactorNotFound() throws Exception {
        mockMvc.perform(delete("/api/factors/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("因子不存在"));
    }
} 