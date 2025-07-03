package com.neulab.fund.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = com.example.background.BackgroundApplication.class)
@AutoConfigureMockMvc
public class UserPositionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllUserPosition() throws Exception {
        mockMvc.perform(get("/api/userposition/all"))
                .andExpect(status().isOk());
    }
} 