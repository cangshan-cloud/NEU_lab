package com.neulab.fund.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户持仓实体
 */
@Entity
@Table(name = "user_position")
public class UserPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "shares", nullable = false, precision = 15, scale = 4)
    private BigDecimal shares;

    @Column(name = "cost", nullable = false, precision = 15, scale = 2)
    private BigDecimal cost;

    @Column(name = "avg_cost_price", precision = 10, scale = 4)
    private BigDecimal avgCostPrice;

    @Column(name = "market_value", precision = 15, scale = 2)
    private BigDecimal marketValue;

    @Column(name = "profit_loss", precision = 15, scale = 2)
    private BigDecimal profitLoss;

    @Column(name = "profit_loss_rate", precision = 8, scale = 4)
    private BigDecimal profitLossRate;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public BigDecimal getShares() { return shares; }
    public void setShares(BigDecimal shares) { this.shares = shares; }
    public BigDecimal getCost() { return cost; }
    public void setCost(BigDecimal cost) { this.cost = cost; }
    public BigDecimal getAvgCostPrice() { return avgCostPrice; }
    public void setAvgCostPrice(BigDecimal avgCostPrice) { this.avgCostPrice = avgCostPrice; }
    public BigDecimal getMarketValue() { return marketValue; }
    public void setMarketValue(BigDecimal marketValue) { this.marketValue = marketValue; }
    public BigDecimal getProfitLoss() { return profitLoss; }
    public void setProfitLoss(BigDecimal profitLoss) { this.profitLoss = profitLoss; }
    public BigDecimal getProfitLossRate() { return profitLossRate; }
    public void setProfitLossRate(BigDecimal profitLossRate) { this.profitLossRate = profitLossRate; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
} 