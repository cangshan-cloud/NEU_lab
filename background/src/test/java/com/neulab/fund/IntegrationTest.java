package com.neulab.fund;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neulab.fund.vo.UserRegisterDTO;
import com.neulab.fund.vo.UserLoginDTO;
import com.neulab.fund.entity.Fund;
import com.neulab.fund.vo.StrategyDTO;
import com.neulab.fund.entity.Factor;
import com.neulab.fund.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = com.example.background.BackgroundApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class IntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testUserRegistrationAndLoginFlow() throws Exception {
        // 使用时间戳确保用户名唯一
        String timestamp = String.valueOf(System.currentTimeMillis());
        String username = "integrationtest" + timestamp;
        
        // 1. 用户注册
        UserRegisterDTO registerDto = new UserRegisterDTO();
        registerDto.setUsername(username);
        registerDto.setPassword("password123");
        registerDto.setEmail("integration" + timestamp + "@test.com");
        registerDto.setRealName("集成测试用户");

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value(username));

        // 2. 用户登录
        UserLoginDTO loginDto = new UserLoginDTO();
        loginDto.setUsername(username);
        loginDto.setPassword("password123");

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.user.username").value(username));
    }

    @Test
    public void testFundManagementFlow() throws Exception {
        // 1. 创建基金
        Fund fund = new Fund();
        fund.setName("集成测试基金");
        fund.setCode("INTEG001");
        fund.setType("股票型");
        fund.setStatus("active");

        mockMvc.perform(post("/api/funds")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fund)))
                .andExpect(status().isOk());

        // 2. 查询基金列表
        mockMvc.perform(get("/api/funds"))
                .andExpect(status().isOk());
    }

    @Test
    public void testStrategyManagementFlow() throws Exception {
        // 1. 创建策略
        StrategyDTO strategy = new StrategyDTO();
        strategy.setStrategyName("集成测试策略");
        strategy.setStrategyCode("STRAT001");
        strategy.setStrategyType("量化策略");
        strategy.setStatus("active");
        strategy.setRiskLevel("中等风险");
        strategy.setTargetReturn(0.15);
        strategy.setMaxDrawdown(0.10);
        strategy.setInvestmentHorizon("长期");
        strategy.setDescription("集成测试策略描述");

        mockMvc.perform(post("/api/strategies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(strategy)))
                .andExpect(status().isOk());

        // 2. 查询策略列表
        mockMvc.perform(get("/api/strategies"))
                .andExpect(status().isOk());
    }

    @Test
    public void testFactorManagementFlow() throws Exception {
        // 1. 创建因子
        Factor factor = new Factor();
        factor.setFactorCode("FACTOR001");
        factor.setFactorName("集成测试因子");
        factor.setFactorCategory("技术因子");
        factor.setFactorType("动量因子");
        factor.setDescription("集成测试因子描述");
        factor.setStatus("active");

        mockMvc.perform(post("/api/factors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(factor)))
                .andExpect(status().isOk());

        // 2. 查询因子列表
        mockMvc.perform(get("/api/factors"))
                .andExpect(status().isOk());
    }

    @Test
    public void testProductManagementFlow() throws Exception {
        // 1. 创建产品
        Product product = new Product();
        product.setProductCode("PROD001");
        product.setProductName("集成测试产品");
        product.setProductType("股票型");
        product.setRiskLevel("中等风险");
        product.setStatus("active");

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk());

        // 2. 查询产品列表
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk());
    }

    @Test
    public void testErrorHandling() throws Exception {
        // 测试错误处理 - 查询不存在的资源
        mockMvc.perform(get("/api/funds/999999"))
                .andExpect(status().isOk());
    }
} 