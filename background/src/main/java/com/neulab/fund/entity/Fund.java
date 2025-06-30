package com.neulab.fund.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

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

    /** 基金代码 */
    private String code;
    /** 基金名称 */
    private String name;
    /** 基金公司ID */
    private Long companyId;
    /** 基金经理ID */
    private Long managerId;
    /** 基金类型 */
    private String type;
    /** 基金状态 */
    private String status;
    /** 创建时间 */
    private LocalDateTime createdAt;
    /** 更新时间 */
    private LocalDateTime updatedAt;
    /** 风险等级 */
    private String riskLevel;

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
} 