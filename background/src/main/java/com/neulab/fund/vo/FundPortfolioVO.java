package com.neulab.fund.vo;

import java.math.BigDecimal;

/**
 * 基金组合列表VO
 */
public class FundPortfolioVO {
    private Long id;
    private String portfolioCode;
    private String portfolioName;
    private String portfolioType;
    private String riskLevel;
    private BigDecimal targetReturn;
    private BigDecimal maxDrawdown;
    private String investmentHorizon;
    private BigDecimal minInvestment;
    private String status;
    private String description;
    private String createdAt;
    private String updatedAt;
    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPortfolioCode() { return portfolioCode; }
    public void setPortfolioCode(String portfolioCode) { this.portfolioCode = portfolioCode; }
    public String getPortfolioName() { return portfolioName; }
    public void setPortfolioName(String portfolioName) { this.portfolioName = portfolioName; }
    public String getPortfolioType() { return portfolioType; }
    public void setPortfolioType(String portfolioType) { this.portfolioType = portfolioType; }
    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    public BigDecimal getTargetReturn() { return targetReturn; }
    public void setTargetReturn(BigDecimal targetReturn) { this.targetReturn = targetReturn; }
    public BigDecimal getMaxDrawdown() { return maxDrawdown; }
    public void setMaxDrawdown(BigDecimal maxDrawdown) { this.maxDrawdown = maxDrawdown; }
    public String getInvestmentHorizon() { return investmentHorizon; }
    public void setInvestmentHorizon(String investmentHorizon) { this.investmentHorizon = investmentHorizon; }
    public BigDecimal getMinInvestment() { return minInvestment; }
    public void setMinInvestment(BigDecimal minInvestment) { this.minInvestment = minInvestment; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}