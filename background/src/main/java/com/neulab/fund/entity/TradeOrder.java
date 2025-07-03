package com.neulab.fund.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易订单实体类
 * 管理用户的交易订单信息
 */
@Data
@Entity
@Table(name = "`trade_order`")
public class TradeOrder {
    
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 订单编号
     */
    @Column(name = "order_no", nullable = false, unique = true, length = 50)
    private String orderNo;
    
    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    /**
     * 产品ID
     */
    @Column(name = "product_id", nullable = false)
    private Long productId;
    
    /**
     * 交易类型：BUY-买入，SELL-卖出
     */
    @Column(name = "trade_type", nullable = false, length = 10)
    private String tradeType;
    
    /**
     * 交易金额
     */
    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;
    
    /**
     * 交易份额
     */
    @Column(name = "shares", precision = 15, scale = 4)
    private BigDecimal shares;
    
    /**
     * 订单状态：PENDING-待处理，PROCESSING-处理中，SUCCESS-成功，FAILED-失败，CANCELLED-已取消
     */
    @Column(name = "status", nullable = false, length = 20)
    private String status;
    
    /**
     * 订单备注
     */
    @Column(name = "remark", length = 500)
    private String remark;
    
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
    
    /**
     * 处理时间
     */
    @Column(name = "processed_at")
    private LocalDateTime processedAt;
} 