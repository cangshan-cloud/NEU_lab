package com.neulab.fund.service;

import com.neulab.fund.entity.TradeRecord;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 交易记录Service接口
 */
public interface TradeRecordService {
    
    /**
     * 创建交易记录
     */
    TradeRecord createRecord(TradeRecord record);
    
    /**
     * 根据ID查询交易记录
     */
    Optional<TradeRecord> findById(Long id);
    
    /**
     * 根据记录编号查询交易记录
     */
    Optional<TradeRecord> findByRecordNo(String recordNo);
    
    /**
     * 查询所有交易记录
     */
    List<TradeRecord> findAll();
    
    /**
     * 根据订单ID查询交易记录
     */
    List<TradeRecord> findByOrderId(Long orderId);
    
    /**
     * 根据用户ID查询交易记录
     */
    List<TradeRecord> findByUserId(Long userId);
    
    /**
     * 根据用户ID和交易类型查询交易记录
     */
    List<TradeRecord> findByUserIdAndTradeType(Long userId, String tradeType);
    
    /**
     * 根据产品ID查询交易记录
     */
    List<TradeRecord> findByProductId(Long productId);
    
    /**
     * 根据状态查询交易记录
     */
    List<TradeRecord> findByStatus(String status);
    
    /**
     * 根据时间范围查询交易记录
     */
    List<TradeRecord> findByTradeTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 更新交易记录
     */
    TradeRecord updateRecord(TradeRecord record);
    
    /**
     * 删除交易记录
     */
    void deleteRecord(Long id);
    
    /**
     * 统计用户交易记录数量
     */
    long countByUserId(Long userId);
    
    /**
     * 统计用户成功交易记录数量
     */
    long countSuccessByUserId(Long userId);
} 