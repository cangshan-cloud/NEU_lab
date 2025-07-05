package com.neulab.fund.vo;

import java.math.BigDecimal;

/**
 * 产品列表VO
 */
public class ProductVO {
    private Long id;
    private String productCode;
    private String productName;
    private String productType;
    private Long strategyId;
    private Long portfolioId;
    private String riskLevel;
    private BigDecimal targetReturn;
    private BigDecimal maxDrawdown;
    private String investmentHorizon;
    private BigDecimal minInvestment;
    private BigDecimal maxInvestment;
    private BigDecimal managementFee;
    private BigDecimal performanceFee;
    private BigDecimal subscriptionFee;
    private BigDecimal redemptionFee;
    private String description;
    private String prospectus;
    private String status;
    private String createdAt;
    private String updatedAt;
    
    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }
    
    public Long getStrategyId() { return strategyId; }
    public void setStrategyId(Long strategyId) { this.strategyId = strategyId; }
    
    public Long getPortfolioId() { return portfolioId; }
    public void setPortfolioId(Long portfolioId) { this.portfolioId = portfolioId; }
    
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
    
    public BigDecimal getMaxInvestment() { return maxInvestment; }
    public void setMaxInvestment(BigDecimal maxInvestment) { this.maxInvestment = maxInvestment; }
    
    public BigDecimal getManagementFee() { return managementFee; }
    public void setManagementFee(BigDecimal managementFee) { this.managementFee = managementFee; }
    
    public BigDecimal getPerformanceFee() { return performanceFee; }
    public void setPerformanceFee(BigDecimal performanceFee) { this.performanceFee = performanceFee; }
    
    public BigDecimal getSubscriptionFee() { return subscriptionFee; }
    public void setSubscriptionFee(BigDecimal subscriptionFee) { this.subscriptionFee = subscriptionFee; }
    
    public BigDecimal getRedemptionFee() { return redemptionFee; }
    public void setRedemptionFee(BigDecimal redemptionFee) { this.redemptionFee = redemptionFee; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getProspectus() { return prospectus; }
    public void setProspectus(String prospectus) { this.prospectus = prospectus; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
} 