package com.neulab.fund.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 基金指数组合实体
 */
@Entity
@Table(name = "`index_portfolio`")
public class IndexPortfolio {
    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** 组合名称 */
    @Column(name = "portfolio_name", nullable = false)
    private String portfolioName;
    /** 组合代码 */
    @Column(name = "portfolio_code", nullable = false, unique = true)
    private String portfolioCode;
    /** 指数代码 */
    @Column(name = "index_code")
    private String indexCode;
    /** 指数名称 */
    @Column(name = "index_name")
    private String indexName;
    /** 组合类型 */
    @Column(name = "portfolio_type")
    private String portfolioType;
    /** 风险等级 */
    @Column(name = "risk_level")
    private String riskLevel;
    /** 目标收益率 */
    @Column(name = "target_return")
    private java.math.BigDecimal targetReturn;
    /** 最大回撤 */
    @Column(name = "max_drawdown")
    private java.math.BigDecimal maxDrawdown;
    /** 投资期限 */
    @Column(name = "investment_horizon")
    private String investmentHorizon;
    /** 最小投资金额 */
    @Column(name = "min_investment")
    private java.math.BigDecimal minInvestment;
    /** 状态 */
    @Column(name = "status")
    private String status;
    /** 创建时间 */
    @Column(name = "created_at")
    private java.time.LocalDateTime createdAt;
    /** 更新时间 */
    @Column(name = "updated_at")
    private java.time.LocalDateTime updatedAt;

    // getter/setter for all fields
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPortfolioName() { return portfolioName; }
    public void setPortfolioName(String portfolioName) { this.portfolioName = portfolioName; }
    public String getPortfolioCode() { return portfolioCode; }
    public void setPortfolioCode(String portfolioCode) { this.portfolioCode = portfolioCode; }
    public String getIndexCode() { return indexCode; }
    public void setIndexCode(String indexCode) { this.indexCode = indexCode; }
    public String getIndexName() { return indexName; }
    public void setIndexName(String indexName) { this.indexName = indexName; }
    public String getPortfolioType() { return portfolioType; }
    public void setPortfolioType(String portfolioType) { this.portfolioType = portfolioType; }
    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    public java.math.BigDecimal getTargetReturn() { return targetReturn; }
    public void setTargetReturn(java.math.BigDecimal targetReturn) { this.targetReturn = targetReturn; }
    public java.math.BigDecimal getMaxDrawdown() { return maxDrawdown; }
    public void setMaxDrawdown(java.math.BigDecimal maxDrawdown) { this.maxDrawdown = maxDrawdown; }
    public String getInvestmentHorizon() { return investmentHorizon; }
    public void setInvestmentHorizon(String investmentHorizon) { this.investmentHorizon = investmentHorizon; }
    public java.math.BigDecimal getMinInvestment() { return minInvestment; }
    public void setMinInvestment(java.math.BigDecimal minInvestment) { this.minInvestment = minInvestment; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public java.time.LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(java.time.LocalDateTime createdAt) { this.createdAt = createdAt; }
    public java.time.LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(java.time.LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
} 