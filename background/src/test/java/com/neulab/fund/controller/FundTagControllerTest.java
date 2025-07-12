package com.neulab.fund.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = com.example.background.BackgroundApplication.class)
@ActiveProfiles("test")
class FundTagControllerTest {
    @Test
    void contextLoads() {
        assertTrue(true);
    }

    // 空置测试方法补充示例：
    // @Autowired
    // private MockMvc mockMvc;
    // @Test
    // public void testListTags() throws Exception {
    //     mockMvc.perform(get("/api/fund-tags"))
    //             .andExpect(status().isOk());
    // }
} 