package com.neulab.fund.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

/**
 * 基金组合实体
 */
@Entity
@Table(name = "fund_portfolio")
public class FundPortfolio {
    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** 组合名称 */
    @Column(name = "portfolio_name")
    private String portfolioName;
    /** 创建时间 */
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    /** 更新时间 */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    /** 组合代码 */
    @Column(name = "portfolio_code")
    private String portfolioCode;
    /** 组合类型 */
    @Column(name = "portfolio_type")
    private String portfolioType;
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
    /** 状态 */
    @Column(name = "status")
    private String status;
    /** 描述 */
    @Column(name = "description")
    private String description;
    /** 名称 */
    @Column(name = "name")
    private String name;
    /** 用户ID */
    @Column(name = "user_id")
    private Long userId;

    @ManyToMany
    @JoinTable(
        name = "fund_portfolio_relation",
        joinColumns = @JoinColumn(name = "portfolio_id"),
        inverseJoinColumns = @JoinColumn(name = "fund_id")
    )
    private List<Fund> funds;

    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPortfolioName() { return portfolioName; }
    public void setPortfolioName(String portfolioName) { this.portfolioName = portfolioName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public List<Fund> getFunds() { return funds; }
    public void setFunds(List<Fund> funds) { this.funds = funds; }
    public String getPortfolioCode() { return portfolioCode; }
    public void setPortfolioCode(String portfolioCode) { this.portfolioCode = portfolioCode; }
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
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
} 