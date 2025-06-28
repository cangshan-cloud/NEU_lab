package com.neulab.fund.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 资金流水实体类
 * 管理资金进出记录
 */
@Data
@Entity
@Table(name = "capital_flow")
public class CapitalFlow {
    
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 流水编号
     */
    @Column(name = "flow_no", nullable = false, unique = true, length = 50)
    private String flowNo;
    
    /**
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    /**
     * 流水类型：DEPOSIT-充值，WITHDRAW-提现，BUY-买入，SELL-卖出，FEE-手续费，DIVIDEND-分红
     */
    @Column(name = "flow_type", nullable = false, length = 20)
    private String flowType;
    
    /**
     * 流水金额
     */
    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;
    
    /**
     * 流水前余额
     */
    @Column(name = "balance_before", precision = 15, scale = 2)
    private BigDecimal balanceBefore;
    
    /**
     * 流水后余额
     */
    @Column(name = "balance_after", precision = 15, scale = 2)
    private BigDecimal balanceAfter;
    
    /**
     * 关联订单ID
     */
    @Column(name = "order_id")
    private Long orderId;
    
    /**
     * 关联产品ID
     */
    @Column(name = "product_id")
    private Long productId;
    
    /**
     * 流水状态：SUCCESS-成功，FAILED-失败，PENDING-处理中
     */
    @Column(name = "status", nullable = false, length = 20)
    private String status;
    
    /**
     * 流水备注
     */
    @Column(name = "remark", length = 500)
    private String remark;
    
    /**
     * 流水时间
     */
    @Column(name = "flow_time", nullable = false)
    private LocalDateTime flowTime;
    
    /**
     * 创建时间
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
} 