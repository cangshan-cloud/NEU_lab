package com.neulab.fund.repository;

import com.neulab.fund.entity.TradeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 交易记录Repository接口
 */
@Repository
public interface TradeRecordRepository extends JpaRepository<TradeRecord, Long> {
    
    /**
     * 根据记录编号查询交易记录
     */
    Optional<TradeRecord> findByRecordNo(String recordNo);
    
    /**
     * 根据订单ID查询交易记录
     */
    List<TradeRecord> findByOrderIdOrderByTradeTimeDesc(Long orderId);
    
    /**
     * 根据用户ID查询交易记录
     */
    List<TradeRecord> findByUserIdOrderByTradeTimeDesc(Long userId);
    
    /**
     * 根据用户ID和交易类型查询交易记录
     */
    List<TradeRecord> findByUserIdAndTradeTypeOrderByTradeTimeDesc(Long userId, String tradeType);
    
    /**
     * 根据产品ID查询交易记录
     */
    List<TradeRecord> findByProductIdOrderByTradeTimeDesc(Long productId);
    
    /**
     * 根据状态查询交易记录
     */
    List<TradeRecord> findByStatusOrderByTradeTimeDesc(String status);
    
    /**
     * 根据时间范围查询交易记录
     */
    @Query("SELECT t FROM TradeRecord t WHERE t.tradeTime BETWEEN :startTime AND :endTime ORDER BY t.tradeTime DESC")
    List<TradeRecord> findByTradeTimeBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计用户交易记录数量
     */
    @Query("SELECT COUNT(t) FROM TradeRecord t WHERE t.userId = :userId")
    long countByUserId(@Param("userId") Long userId);
    
    /**
     * 统计用户成功交易记录数量
     */
    @Query("SELECT COUNT(t) FROM TradeRecord t WHERE t.userId = :userId AND t.status = 'SUCCESS'")
    long countSuccessByUserId(@Param("userId") Long userId);
} 