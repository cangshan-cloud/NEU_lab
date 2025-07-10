package com.neulab.fund.vo;

import java.time.LocalDate;

/**
 * 策略回测参数DTO
 */
public class StrategyBacktestDTO {
    /** 策略ID */
    private Long strategyId;
    /** 回测名称 */
    private String backtestName;
    /** 回测类型 */
    private String backtestType;
    /** 回测起始日期 */
    private LocalDate startDate;
    /** 回测结束日期 */
    private LocalDate endDate;
    /** 初始资金 */
    private Double initialCapital;
    /** 回测参数JSON */
    private String parameters;

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
    public String getParameters() { return parameters; }
    public void setParameters(String parameters) { this.parameters = parameters; }
} 