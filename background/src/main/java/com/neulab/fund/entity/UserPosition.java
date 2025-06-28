package com.neulab.fund.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户持仓实体类
 * 管理用户当前持仓信息
 */
@Data
@Entity
@Table(name = "user_position")
public class UserPosition {
    
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    /**
     * 产品ID
     */
    @Column(name = "product_id", nullable = false)
    private Long productId;
    
    /**
     * 持仓份额
     */
    @Column(name = "shares", nullable = false, precision = 15, scale = 4)
    private BigDecimal shares;
    
    /**
     * 持仓成本
     */
    @Column(name = "cost", nullable = false, precision = 15, scale = 2)
    private BigDecimal cost;
    
    /**
     * 平均成本价
     */
    @Column(name = "avg_cost_price", precision = 10, scale = 4)
    private BigDecimal avgCostPrice;
    
    /**
     * 当前市值
     */
    @Column(name = "market_value", precision = 15, scale = 2)
    private BigDecimal marketValue;
    
    /**
     * 浮动盈亏
     */
    @Column(name = "profit_loss", precision = 15, scale = 2)
    private BigDecimal profitLoss;
    
    /**
     * 浮动盈亏率
     */
    @Column(name = "profit_loss_rate", precision = 8, scale = 4)
    private BigDecimal profitLossRate;
    
    /**
     * 最后更新时间
     */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
} 