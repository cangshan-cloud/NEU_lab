package com.neulab.fund.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 产品收益实体
 */
@Entity
@Table(name = "product_performance")
public class ProductPerformance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "performance_date", nullable = false)
    private LocalDate performanceDate;

    @Column(name = "nav", precision = 10, scale = 4)
    private BigDecimal nav;

    @Column(name = "cumulative_return", precision = 8, scale = 4)
    private BigDecimal cumulativeReturn;

    @Column(name = "daily_return", precision = 8, scale = 4)
    private BigDecimal dailyReturn;

    @Column(name = "weekly_return", precision = 8, scale = 4)
    private BigDecimal weeklyReturn;

    @Column(name = "monthly_return", precision = 8, scale = 4)
    private BigDecimal monthlyReturn;

    @Column(name = "quarterly_return", precision = 8, scale = 4)
    private BigDecimal quarterlyReturn;

    @Column(name = "yearly_return", precision = 8, scale = 4)
    private BigDecimal yearlyReturn;

    @Column(name = "max_drawdown", precision = 8, scale = 4)
    private BigDecimal maxDrawdown;

    @Column(name = "sharpe_ratio", precision = 8, scale = 4)
    private BigDecimal sharpeRatio;

    @Column(name = "volatility", precision = 8, scale = 4)
    private BigDecimal volatility;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public LocalDate getPerformanceDate() { return performanceDate; }
    public void setPerformanceDate(LocalDate performanceDate) { this.performanceDate = performanceDate; }
    public BigDecimal getNav() { return nav; }
    public void setNav(BigDecimal nav) { this.nav = nav; }
    public BigDecimal getCumulativeReturn() { return cumulativeReturn; }
    public void setCumulativeReturn(BigDecimal cumulativeReturn) { this.cumulativeReturn = cumulativeReturn; }
    public BigDecimal getDailyReturn() { return dailyReturn; }
    public void setDailyReturn(BigDecimal dailyReturn) { this.dailyReturn = dailyReturn; }
    public BigDecimal getWeeklyReturn() { return weeklyReturn; }
    public void setWeeklyReturn(BigDecimal weeklyReturn) { this.weeklyReturn = weeklyReturn; }
    public BigDecimal getMonthlyReturn() { return monthlyReturn; }
    public void setMonthlyReturn(BigDecimal monthlyReturn) { this.monthlyReturn = monthlyReturn; }
    public BigDecimal getQuarterlyReturn() { return quarterlyReturn; }
    public void setQuarterlyReturn(BigDecimal quarterlyReturn) { this.quarterlyReturn = quarterlyReturn; }
    public BigDecimal getYearlyReturn() { return yearlyReturn; }
    public void setYearlyReturn(BigDecimal yearlyReturn) { this.yearlyReturn = yearlyReturn; }
    public BigDecimal getMaxDrawdown() { return maxDrawdown; }
    public void setMaxDrawdown(BigDecimal maxDrawdown) { this.maxDrawdown = maxDrawdown; }
    public BigDecimal getSharpeRatio() { return sharpeRatio; }
    public void setSharpeRatio(BigDecimal sharpeRatio) { this.sharpeRatio = sharpeRatio; }
    public BigDecimal getVolatility() { return volatility; }
    public void setVolatility(BigDecimal volatility) { this.volatility = volatility; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
} 