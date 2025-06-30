package com.neulab.fund.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
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
    @Column(name = "name")
    private String portfolioName;
    /** 创建时间 */
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    /** 更新时间 */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

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