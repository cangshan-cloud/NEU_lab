package com.neulab.fund.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易记录实体类
 * 记录所有交易历史信息
 */
@Data
@Entity
@Table(name = "`trade_record`")
public class TradeRecord {
    
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 交易记录编号
     */
    @Column(name = "record_no", nullable = false, unique = true, length = 50)
    private String recordNo;
    
    /**
     * 订单ID
     */
    @Column(name = "order_id", nullable = false)
    private Long orderId;
    
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
     * 交易价格
     */
    @Column(name = "price", precision = 10, scale = 4)
    private BigDecimal price;
    
    /**
     * 手续费
     */
    @Column(name = "fee", precision = 10, scale = 2)
    private BigDecimal fee;
    
    /**
     * 交易状态：SUCCESS-成功，FAILED-失败
     */
    @Column(name = "status", nullable = false, length = 20)
    private String status;
    
    /**
     * 交易时间
     */
    @Column(name = "trade_time", nullable = false)
    private LocalDateTime tradeTime;
    
    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
} 