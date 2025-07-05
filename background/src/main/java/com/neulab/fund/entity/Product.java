package com.neulab.fund.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 组合产品实体
 */
@Entity
@Table(name = "`product`")
public class Product {
    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** 产品代码 */
    @Column(name = "product_code")
    private String productCode;
    
    /** 产品名称 */
    @Column(name = "product_name")
    private String productName;
    
    /** 产品类型 */
    @Column(name = "product_type")
    private String productType;
    
    /** 关联策略ID */
    @Column(name = "strategy_id")
    private Long strategyId;
    
    /** 关联组合ID */
    @Column(name = "portfolio_id")
    private Long portfolioId;
    
    /** 风险等级 */
    @Column(name = "risk_level")
    private String riskLevel;
    
    /** 目标收益率 */
    @Column(name = "target_return")
    private BigDecimal targetReturn;
    
    /** 最大回撤 */
    @Column(name = "max_drawdown")
    private BigDecimal maxDrawdown;
    
    /** 投资期限 */
    @Column(name = "investment_horizon")
    private String investmentHorizon;
    
    /** 最小投资金额 */
    @Column(name = "min_investment")
    private BigDecimal minInvestment;
    
    /** 最大投资金额 */
    @Column(name = "max_investment")
    private BigDecimal maxInvestment;
    
    /** 管理费率 */
    @Column(name = "management_fee")
    private BigDecimal managementFee;
    
    /** 业绩费率 */
    @Column(name = "performance_fee")
    private BigDecimal performanceFee;
    
    /** 认购费率 */
    @Column(name = "subscription_fee")
    private BigDecimal subscriptionFee;
    
    /** 赎回费率 */
    @Column(name = "redemption_fee")
    private BigDecimal redemptionFee;
    
    /** 产品描述 */
    @Column(name = "description")
    private String description;
    
    /** 产品说明书 */
    @Column(name = "prospectus")
    private String prospectus;
    
    /** 产品状态 */
    @Column(name = "status")
    private String status;
    
    /** 创建时间 */
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    /** 更新时间 */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

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
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
} 