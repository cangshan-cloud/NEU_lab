package com.neulab.fund.repository;

import com.neulab.fund.entity.TradeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 交易订单Repository接口
 */
@Repository
public interface TradeOrderRepository extends JpaRepository<TradeOrder, Long> {
    
    /**
     * 根据订单编号查询订单
     */
    Optional<TradeOrder> findByOrderNo(String orderNo);
    
    /**
     * 根据用户ID查询订单列表
     */
    List<TradeOrder> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    /**
     * 根据用户ID和状态查询订单列表
     */
    List<TradeOrder> findByUserIdAndStatusOrderByCreatedAtDesc(Long userId, String status);
    
    /**
     * 根据产品ID查询订单列表
     */
    List<TradeOrder> findByProductIdOrderByCreatedAtDesc(Long productId);
    
    /**
     * 根据状态查询订单列表
     */
    List<TradeOrder> findByStatusOrderByCreatedAtDesc(String status);
    
    /**
     * 统计用户订单数量
     */
    @Query("SELECT COUNT(t) FROM TradeOrder t WHERE t.userId = :userId")
    long countByUserId(@Param("userId") Long userId);
    
    /**
     * 统计用户成功订单数量
     */
    @Query("SELECT COUNT(t) FROM TradeOrder t WHERE t.userId = :userId AND t.status = 'SUCCESS'")
    long countSuccessByUserId(@Param("userId") Long userId);
} 