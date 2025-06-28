package com.neulab.fund.service;

import com.neulab.fund.entity.CapitalFlow;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 资金流水Service接口
 */
public interface CapitalFlowService {
    
    /**
     * 创建资金流水
     */
    CapitalFlow createFlow(CapitalFlow flow);
    
    /**
     * 根据ID查询资金流水
     */
    Optional<CapitalFlow> findById(Long id);
    
    /**
     * 根据流水编号查询资金流水
     */
    Optional<CapitalFlow> findByFlowNo(String flowNo);
    
    /**
     * 查询所有资金流水
     */
    List<CapitalFlow> findAll();
    
    /**
     * 根据用户ID查询资金流水
     */
    List<CapitalFlow> findByUserId(Long userId);
    
    /**
     * 根据用户ID和流水类型查询资金流水
     */
    List<CapitalFlow> findByUserIdAndFlowType(Long userId, String flowType);
    
    /**
     * 根据用户ID和状态查询资金流水
     */
    List<CapitalFlow> findByUserIdAndStatus(Long userId, String status);
    
    /**
     * 根据订单ID查询资金流水
     */
    List<CapitalFlow> findByOrderId(Long orderId);
    
    /**
     * 根据产品ID查询资金流水
     */
    List<CapitalFlow> findByProductId(Long productId);
    
    /**
     * 根据时间范围查询资金流水
     */
    List<CapitalFlow> findByFlowTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 更新资金流水
     */
    CapitalFlow updateFlow(CapitalFlow flow);
    
    /**
     * 删除资金流水
     */
    void deleteFlow(Long id);
    
    /**
     * 统计用户资金流水数量
     */
    long countByUserId(Long userId);
    
    /**
     * 统计用户成功资金流水数量
     */
    long countSuccessByUserId(Long userId);
    
    /**
     * 查询用户资金流水总金额
     */
    BigDecimal sumAmountByUserId(Long userId);
    
    /**
     * 查询用户指定类型资金流水总金额
     */
    BigDecimal sumAmountByUserIdAndFlowType(Long userId, String flowType);
} 