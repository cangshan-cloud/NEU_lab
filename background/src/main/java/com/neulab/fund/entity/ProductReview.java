package com.neulab.fund.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 产品审核实体类
 * 管理产品审核流程
 */
@Data
@Entity
@Table(name = "product_review")
public class ProductReview {
    
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 产品ID
     */
    @Column(name = "product_id", nullable = false)
    private Long productId;
    
    /**
     * 审核人ID
     */
    @Column(name = "reviewer_id")
    private Long reviewerId;
    
    /**
     * 审核类型：INITIAL-初审，FINAL-终审
     */
    @Column(name = "review_type", length = 20)
    private String reviewType;
    
    /**
     * 审核状态：PENDING-待审核，APPROVED-通过，REJECTED-拒绝
     */
    @Column(name = "review_status", length = 20)
    private String reviewStatus;
    
    /**
     * 审核意见
     */
    @Column(name = "review_comment", columnDefinition = "TEXT")
    private String reviewComment;
    
    /**
     * 审核时间
     */
    @Column(name = "review_date")
    private LocalDateTime reviewDate;
    
    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
} 