package com.neulab.fund.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 因子实体
 */
@Entity
@Table(name = "factor")
public class Factor {
    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** 因子代码 */
    private String code;
    
    /** 因子名称 */
    private String name;
    
    /** 因子分类 */
    private String category;
    
    /** 因子类型 */
    private String type;
    
    /** 因子描述 */
    private String description;
    
    /** 计算公式 */
    private String calculationFormula;
    
    /** 数据来源 */
    private String dataSource;
    
    /** 更新频率 */
    private String updateFrequency;
    
    /** 状态 */
    private String status;
    
    /** 创建时间 */
    private LocalDateTime createdAt;
    
    /** 更新时间 */
    private LocalDateTime updatedAt;

    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getCalculationFormula() { return calculationFormula; }
    public void setCalculationFormula(String calculationFormula) { this.calculationFormula = calculationFormula; }
    
    public String getDataSource() { return dataSource; }
    public void setDataSource(String dataSource) { this.dataSource = dataSource; }
    
    public String getUpdateFrequency() { return updateFrequency; }
    public void setUpdateFrequency(String updateFrequency) { this.updateFrequency = updateFrequency; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
} 