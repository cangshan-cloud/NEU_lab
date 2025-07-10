package com.neulab.fund.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 策略回测结果VO
 */
public class StrategyBacktestVO {
    private Long id;
    private Long strategyId;
    private String backtestName;
    private String backtestType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double initialCapital;
    private BigDecimal finalValue;
    private Double totalReturn;
    private Double annualReturn;
    private Double maxDrawdown;
    private Double sharpeRatio;
    private BigDecimal volatility;
    private BigDecimal winRate;
    private String backtestResult;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String results;

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
    public BigDecimal getFinalValue() { return finalValue; }
    public void setFinalValue(BigDecimal finalValue) { this.finalValue = finalValue; }
    public Double getTotalReturn() { return totalReturn; }
    public void setTotalReturn(Double totalReturn) { this.totalReturn = totalReturn; }
    public Double getAnnualReturn() { return annualReturn; }
    public void setAnnualReturn(Double annualReturn) { this.annualReturn = annualReturn; }
    public Double getMaxDrawdown() { return maxDrawdown; }
    public void setMaxDrawdown(Double maxDrawdown) { this.maxDrawdown = maxDrawdown; }
    public Double getSharpeRatio() { return sharpeRatio; }
    public void setSharpeRatio(Double sharpeRatio) { this.sharpeRatio = sharpeRatio; }
    public BigDecimal getVolatility() { return volatility; }
    public void setVolatility(BigDecimal volatility) { this.volatility = volatility; }
    public BigDecimal getWinRate() { return winRate; }
    public void setWinRate(BigDecimal winRate) { this.winRate = winRate; }
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