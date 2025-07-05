package com.neulab.fund.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 基金实体
 */
@Entity
@Table(name = "fund")
public class Fund {
    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fund_code")
    private String fundCode;
    @Column(name = "fund_name")
    private String fundName;
    @Column(name = "company_id")
    private Long companyId;
    @Column(name = "manager_id")
    private Long managerId;
    @Column(name = "inception_date")
    private LocalDate inceptionDate;
    @Column(name = "fund_size")
    private BigDecimal fundSize;
    @Column(name = "nav")
    private BigDecimal nav;
    @Column(name = "nav_date")
    private LocalDate navDate;
    @Column(name = "risk_level")
    private String riskLevel;
    @Column(name = "investment_strategy")
    private String investmentStrategy;
    @Column(name = "benchmark")
    private String benchmark;
    @Column(name = "status")
    private String status;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;

    @ManyToMany
    @JoinTable(
        name = "fund_tag_relation",
        joinColumns = @JoinColumn(name = "fund_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private java.util.List<FundTag> tags;

    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    public Long getManagerId() { return managerId; }
    public void setManagerId(Long managerId) { this.managerId = managerId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    public java.util.List<FundTag> getTags() { return tags; }
    public void setTags(java.util.List<FundTag> tags) { this.tags = tags; }
    public String getFundCode() { return fundCode; }
    public void setFundCode(String fundCode) { this.fundCode = fundCode; }
    public String getFundName() { return fundName; }
    public void setFundName(String fundName) { this.fundName = fundName; }
    public LocalDate getInceptionDate() { return inceptionDate; }
    public void setInceptionDate(LocalDate inceptionDate) { this.inceptionDate = inceptionDate; }
    public BigDecimal getFundSize() { return fundSize; }
    public void setFundSize(BigDecimal fundSize) { this.fundSize = fundSize; }
    public BigDecimal getNav() { return nav; }
    public void setNav(BigDecimal nav) { this.nav = nav; }
    public LocalDate getNavDate() { return navDate; }
    public void setNavDate(LocalDate navDate) { this.navDate = navDate; }
    public String getInvestmentStrategy() { return investmentStrategy; }
    public void setInvestmentStrategy(String investmentStrategy) { this.investmentStrategy = investmentStrategy; }
    public String getBenchmark() { return benchmark; }
    public void setBenchmark(String benchmark) { this.benchmark = benchmark; }
} 