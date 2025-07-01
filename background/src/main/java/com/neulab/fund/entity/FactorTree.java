package com.neulab.fund.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 因子树实体
 */
@Entity
@Table(name = "factor_tree")
public class FactorTree {
    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** 因子树名称 */
    private String treeName;
    /** 因子树代码 */
    private String treeCode;
    /** 因子树描述 */
    private String treeDescription;
    /** 状态 */
    private String status;
    /** 创建时间 */
    private LocalDateTime createdAt;
    /** 更新时间 */
    private LocalDateTime updatedAt;

    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTreeName() { return treeName; }
    public void setTreeName(String treeName) { this.treeName = treeName; }
    public String getTreeCode() { return treeCode; }
    public void setTreeCode(String treeCode) { this.treeCode = treeCode; }
    public String getTreeDescription() { return treeDescription; }
    public void setTreeDescription(String treeDescription) { this.treeDescription = treeDescription; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
} 