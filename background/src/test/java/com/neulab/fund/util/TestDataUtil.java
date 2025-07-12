package com.neulab.fund.util;

import com.neulab.fund.entity.*;
import com.neulab.fund.vo.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试数据工具类
 * 提供常用的测试数据生成方法
 */
public class TestDataUtil {

    /**
     * 创建测试用户
     */
    public static User createTestUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("encoded123456");
        user.setRealName("测试用户");
        user.setEmail("test@example.com");
        user.setPhone("13800138000");
        user.setStatus("ACTIVE");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    /**
     * 创建测试用户注册DTO
     */
    public static UserRegisterDTO createUserRegisterDTO() {
        UserRegisterDTO dto = new UserRegisterDTO();
        dto.setUsername("testuser");
        dto.setPassword("123456");
        dto.setRealName("测试用户");
        dto.setEmail("test@example.com");
        dto.setPhone("13800138000");
        return dto;
    }

    /**
     * 创建测试用户登录DTO
     */
    public static UserLoginDTO createUserLoginDTO() {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setUsername("testuser");
        dto.setPassword("123456");
        return dto;
    }

    /**
     * 创建测试基金
     */
    public static Fund createTestFund() {
        Fund fund = new Fund();
        fund.setFundCode("FUND001");
        fund.setFundName("测试基金");
        fund.setStatus("ACTIVE");
        fund.setCreatedAt(LocalDateTime.now());
        fund.setUpdatedAt(LocalDateTime.now());
        return fund;
    }

    /**
     * 创建测试基金公司
     */
    public static FundCompany createTestFundCompany() {
        FundCompany company = new FundCompany();
        company.setCompanyCode("COMP001");
        company.setCompanyName("测试基金公司");
        company.setStatus("ACTIVE");
        company.setCreatedAt(LocalDateTime.now());
        company.setUpdatedAt(LocalDateTime.now());
        return company;
    }

    /**
     * 创建测试基金经理
     */
    public static FundManager createTestFundManager() {
        FundManager manager = new FundManager();
        manager.setManagerCode("MGR001");
        manager.setManagerName("测试经理");
        manager.setStatus("ACTIVE");
        manager.setCreatedAt(LocalDateTime.now());
        manager.setUpdatedAt(LocalDateTime.now());
        return manager;
    }

    /**
     * 创建测试策略
     */
    public static Strategy createTestStrategy() {
        Strategy strategy = new Strategy();
        strategy.setStrategyCode("STRAT001");
        strategy.setStrategyName("测试策略");
        strategy.setStrategyType("MOMENTUM");
        strategy.setStatus("ACTIVE");
        strategy.setCreatedAt(LocalDateTime.now());
        strategy.setUpdatedAt(LocalDateTime.now());
        return strategy;
    }

    /**
     * 创建测试因子
     */
    public static Factor createTestFactor() {
        Factor factor = new Factor();
        factor.setFactorCode("FACTOR001");
        factor.setFactorName("测试因子");
        factor.setFactorType("MOMENTUM");
        factor.setStatus("ACTIVE");
        factor.setCreatedAt(LocalDateTime.now());
        factor.setUpdatedAt(LocalDateTime.now());
        return factor;
    }

    /**
     * 创建测试产品
     */
    public static Product createTestProduct() {
        Product product = new Product();
        product.setProductCode("PROD001");
        product.setProductName("测试产品");
        product.setStatus("ACTIVE");
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return product;
    }

    /**
     * 创建测试数据列表
     */
    public static List<Fund> createTestFundList() {
        List<Fund> funds = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Fund fund = createTestFund();
            fund.setFundCode("FUND00" + i);
            fund.setFundName("测试基金" + i);
            funds.add(fund);
        }
        return funds;
    }

    /**
     * 创建测试策略列表
     */
    public static List<Strategy> createTestStrategyList() {
        List<Strategy> strategies = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Strategy strategy = createTestStrategy();
            strategy.setStrategyCode("STRAT00" + i);
            strategy.setStrategyName("测试策略" + i);
            strategies.add(strategy);
        }
        return strategies;
    }

    /**
     * 创建测试因子列表
     */
    public static List<Factor> createTestFactorList() {
        List<Factor> factors = new ArrayList<>();
        String[] types = {"MOMENTUM", "VALUE", "QUALITY"};
        for (int i = 1; i <= 3; i++) {
            Factor factor = createTestFactor();
            factor.setFactorCode("FACTOR00" + i);
            factor.setFactorName("测试因子" + i);
            factor.setFactorType(types[i - 1]);
            factors.add(factor);
        }
        return factors;
    }

    /**
     * 创建测试Map数据
     */
    public static Map<String, Object> createTestMapData() {
        Map<String, Object> data = new HashMap<>();
        data.put("name", "测试数据");
        data.put("value", 100);
        data.put("type", "TEST");
        data.put("description", "测试描述");
        return data;
    }

    /**
     * 创建测试API响应数据
     */
    public static Map<String, Object> createTestApiResponse() {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 0);
        response.put("message", "success");
        response.put("data", createTestMapData());
        return response;
    }
} 