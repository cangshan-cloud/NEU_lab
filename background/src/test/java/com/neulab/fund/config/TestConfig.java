package com.neulab.fund.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

/**
 * 测试配置类
 */
@TestConfiguration
@ActiveProfiles("test")
public class TestConfig {
    
    // 可以在这里添加测试专用的Bean配置
} 