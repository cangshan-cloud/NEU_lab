package com.neulab.fund.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 策略组合关联实体类
 * 管理策略与组合的关联关系
 */
@Data
@Entity
@Table(name = "strategy_portfolio")
public class StrategyPortfolio {
    
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 策略ID
     */
    @Column(name = "strategy_id", nullable = false)
    private Long strategyId;
    
    /**
     * 组合ID
     */
    @Column(name = "portfolio_id", nullable = false)
    private Long portfolioId;
    
    /**
     * 权重
     */
    @Column(name = "weight", precision = 5, scale = 4)
    private BigDecimal weight;
    
    /**
     * 状态：ACTIVE-正常，INACTIVE-停用
     */
    @Column(name = "status", length = 20)
    private String status;
    
    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
} 