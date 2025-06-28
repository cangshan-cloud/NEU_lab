package com.neulab.fund.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger（OpenAPI）配置
 */
@Configuration
public class SwaggerConfig {
    /**
     * 配置OpenAPI文档信息
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("智能投顾系统API")
            .version("1.0")
            .description("智能投顾系统后端接口文档"));
    }
} 