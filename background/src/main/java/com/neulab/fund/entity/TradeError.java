package com.neulab.fund.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 差错处理实体
 */
@Entity
@Table(name = "trade_error")
public class TradeError {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "delivery_order_id", nullable = false)
    private Long deliveryOrderId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "remark")
    private String remark;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getDeliveryOrderId() { return deliveryOrderId; }
    public void setDeliveryOrderId(Long deliveryOrderId) { this.deliveryOrderId = deliveryOrderId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
} 