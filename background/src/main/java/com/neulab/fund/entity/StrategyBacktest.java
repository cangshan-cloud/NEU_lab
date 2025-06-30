package com.neulab.fund.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 策略回测实体
 */
@Entity
@Table(name = "strategy_backtest")
public class StrategyBacktest {
    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** 策略ID */
    private Long strategyId;
    
    /** 回测类型 */
    private String backtestType;
    
    /** 回测起始日期 */
    private LocalDate startDate;
    
    /** 回测结束日期 */
    private LocalDate endDate;
    
    /** 初始资金 */
    private Double initialCapital;
    
    /** 总收益率 */
    private Double totalReturn;
    
    /** 年化收益率 */
    private Double annualReturn;
    
    /** 最大回撤 */
    private Double maxDrawdown;
    
    /** 夏普比率 */
    private Double sharpeRatio;
    
    /** 回测结果（可用JSON或文本存储） */
    @Lob
    private String results;
    
    /** 状态 */
    private String status;
    
    /** 创建时间 */
    private LocalDateTime createdAt;
    
    /** 更新时间 */
    private LocalDateTime updatedAt;

    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getStrategyId() { return strategyId; }
    public void setStrategyId(Long strategyId) { this.strategyId = strategyId; }
    
    public String getBacktestType() { return backtestType; }
    public void setBacktestType(String backtestType) { this.backtestType = backtestType; }
    
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    
    public Double getInitialCapital() { return initialCapital; }
    public void setInitialCapital(Double initialCapital) { this.initialCapital = initialCapital; }
    
    public Double getTotalReturn() { return totalReturn; }
    public void setTotalReturn(Double totalReturn) { this.totalReturn = totalReturn; }
    
    public Double getAnnualReturn() { return annualReturn; }
    public void setAnnualReturn(Double annualReturn) { this.annualReturn = annualReturn; }
    
    public Double getMaxDrawdown() { return maxDrawdown; }
    public void setMaxDrawdown(Double maxDrawdown) { this.maxDrawdown = maxDrawdown; }
    
    public Double getSharpeRatio() { return sharpeRatio; }
    public void setSharpeRatio(Double sharpeRatio) { this.sharpeRatio = sharpeRatio; }
    
    public String getResults() { return results; }
    public void setResults(String results) { this.results = results; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
} 