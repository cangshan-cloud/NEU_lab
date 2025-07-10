package com.neulab.fund.vo;

import java.time.LocalDateTime;

/**
 * 策略展示VO
 */
public class StrategyVO {
    /** 主键ID */
    private Long id;
    /** 策略代码 */
    private String strategyCode;
    /** 策略名称 */
    private String strategyName;
    /** 策略类型 */
    private String strategyType;
    /** 风险等级 */
    private String riskLevel;
    /** 目标收益率 */
    private Double targetReturn;
    /** 最大回撤 */
    private Double maxDrawdown;
    /** 投资期限 */
    private String investmentHorizon;
    /** 策略描述 */
    private String description;
    /** 关联因子树ID */
    private Long factorTreeId;
    /** 策略参数JSON */
    private String parameters;
    /** 状态 */
    private String status;
    /** 创建时间 */
    private LocalDateTime createdAt;
    /** 更新时间 */
    private LocalDateTime updatedAt;
    // 可扩展：如组合、回测等

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStrategyCode() { return strategyCode; }
    public void setStrategyCode(String strategyCode) { this.strategyCode = strategyCode; }
    public String getStrategyName() { return strategyName; }
    public void setStrategyName(String strategyName) { this.strategyName = strategyName; }
    public String getStrategyType() { return strategyType; }
    public void setStrategyType(String strategyType) { this.strategyType = strategyType; }
    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    public Double getTargetReturn() { return targetReturn; }
    public void setTargetReturn(Double targetReturn) { this.targetReturn = targetReturn; }
    public Double getMaxDrawdown() { return maxDrawdown; }
    public void setMaxDrawdown(Double maxDrawdown) { this.maxDrawdown = maxDrawdown; }
    public String getInvestmentHorizon() { return investmentHorizon; }
    public void setInvestmentHorizon(String investmentHorizon) { this.investmentHorizon = investmentHorizon; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getFactorTreeId() { return factorTreeId; }
    public void setFactorTreeId(Long factorTreeId) { this.factorTreeId = factorTreeId; }
    public String getParameters() { return parameters; }
    public void setParameters(String parameters) { this.parameters = parameters; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
} 