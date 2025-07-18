package com.neulab.fund.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 策略回测实体
 */
@Entity
@Table(name = "`strategy_backtest`")
public class StrategyBacktest {
    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** 策略ID */
    @Column(name = "strategy_id", nullable = false)
    private Long strategyId;
    /** 回测名称 */
    @Column(name = "backtest_name", nullable = false)
    private String backtestName;
    /** 回测类型 */
    @Column(name = "backtest_type")
    private String backtestType;
    /** 回测起始日期 */
    @Column(name = "start_date", nullable = false)
    private java.time.LocalDate startDate;
    /** 回测结束日期 */
    @Column(name = "end_date", nullable = false)
    private java.time.LocalDate endDate;
    /** 初始资金 */
    @Column(name = "initial_capital")
    private Double initialCapital;
    /** 最终价值 */
    @Column(name = "final_value")
    private java.math.BigDecimal finalValue;
    /** 总收益率 */
    @Column(name = "total_return")
    private Double totalReturn;
    /** 年化收益率 */
    @Column(name = "annual_return")
    private Double annualReturn;
    /** 最大回撤 */
    @Column(name = "max_drawdown")
    private Double maxDrawdown;
    /** 夏普比率 */
    @Column(name = "sharpe_ratio")
    private Double sharpeRatio;
    /** 波动率 */
    @Column(name = "volatility")
    private java.math.BigDecimal volatility;
    /** 胜率 */
    @Column(name = "win_rate")
    private java.math.BigDecimal winRate;
    /** 回测结果JSON */
    @Lob
    @Column(name = "backtest_result")
    private String backtestResult;
    /** 状态 */
    @Column(name = "status")
    private String status;
    /** 创建时间 */
    @Column(name = "created_at")
    private java.time.LocalDateTime createdAt;
    /** 更新时间 */
    @Column(name = "updated_at")
    private java.time.LocalDateTime updatedAt;
    /** 回测结果明细（JSON或文本） */
    @Lob
    @Column(name = "results")
    private String results;

    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getStrategyId() { return strategyId; }
    public void setStrategyId(Long strategyId) { this.strategyId = strategyId; }
    
    public String getBacktestName() { return backtestName; }
    public void setBacktestName(String backtestName) { this.backtestName = backtestName; }
    
    public String getBacktestType() { return backtestType; }
    public void setBacktestType(String backtestType) { this.backtestType = backtestType; }
    
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    
    public Double getInitialCapital() { return initialCapital; }
    public void setInitialCapital(Double initialCapital) { this.initialCapital = initialCapital; }
    
    public java.math.BigDecimal getFinalValue() { return finalValue; }
    public void setFinalValue(java.math.BigDecimal finalValue) { this.finalValue = finalValue; }
    
    public Double getTotalReturn() { return totalReturn; }
    public void setTotalReturn(Double totalReturn) { this.totalReturn = totalReturn; }
    
    public Double getAnnualReturn() { return annualReturn; }
    public void setAnnualReturn(Double annualReturn) { this.annualReturn = annualReturn; }
    
    public Double getMaxDrawdown() { return maxDrawdown; }
    public void setMaxDrawdown(Double maxDrawdown) { this.maxDrawdown = maxDrawdown; }
    
    public Double getSharpeRatio() { return sharpeRatio; }
    public void setSharpeRatio(Double sharpeRatio) { this.sharpeRatio = sharpeRatio; }
    
    public java.math.BigDecimal getVolatility() { return volatility; }
    public void setVolatility(java.math.BigDecimal volatility) { this.volatility = volatility; }
    
    public java.math.BigDecimal getWinRate() { return winRate; }
    public void setWinRate(java.math.BigDecimal winRate) { this.winRate = winRate; }
    
    public String getBacktestResult() { return backtestResult; }
    public void setBacktestResult(String backtestResult) { this.backtestResult = backtestResult; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getResults() { return results; }
    public void setResults(String results) { this.results = results; }
} 