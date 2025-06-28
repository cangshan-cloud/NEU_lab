package com.neulab.fund.repository;

import com.neulab.fund.entity.CapitalFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 资金流水Repository接口
 */
@Repository
public interface CapitalFlowRepository extends JpaRepository<CapitalFlow, Long> {
    
    /**
     * 根据流水编号查询资金流水
     */
    Optional<CapitalFlow> findByFlowNo(String flowNo);
    
    /**
     * 根据用户ID查询资金流水
     */
    List<CapitalFlow> findByUserIdOrderByFlowTimeDesc(Long userId);
    
    /**
     * 根据用户ID和流水类型查询资金流水
     */
    List<CapitalFlow> findByUserIdAndFlowTypeOrderByFlowTimeDesc(Long userId, String flowType);
    
    /**
     * 根据用户ID和状态查询资金流水
     */
    List<CapitalFlow> findByUserIdAndStatusOrderByFlowTimeDesc(Long userId, String status);
    
    /**
     * 根据订单ID查询资金流水
     */
    List<CapitalFlow> findByOrderIdOrderByFlowTimeDesc(Long orderId);
    
    /**
     * 根据产品ID查询资金流水
     */
    List<CapitalFlow> findByProductIdOrderByFlowTimeDesc(Long productId);
    
    /**
     * 根据时间范围查询资金流水
     */
    @Query("SELECT c FROM CapitalFlow c WHERE c.flowTime BETWEEN :startTime AND :endTime ORDER BY c.flowTime DESC")
    List<CapitalFlow> findByFlowTimeBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
    
    /**
     * 统计用户资金流水数量
     */
    @Query("SELECT COUNT(c) FROM CapitalFlow c WHERE c.userId = :userId")
    long countByUserId(@Param("userId") Long userId);
    
    /**
     * 统计用户成功资金流水数量
     */
    @Query("SELECT COUNT(c) FROM CapitalFlow c WHERE c.userId = :userId AND c.status = 'SUCCESS'")
    long countSuccessByUserId(@Param("userId") Long userId);
    
    /**
     * 查询用户资金流水总金额
     */
    @Query("SELECT COALESCE(SUM(c.amount), 0) FROM CapitalFlow c WHERE c.userId = :userId AND c.status = 'SUCCESS'")
    BigDecimal sumAmountByUserId(@Param("userId") Long userId);
    
    /**
     * 查询用户指定类型资金流水总金额
     */
    @Query("SELECT COALESCE(SUM(c.amount), 0) FROM CapitalFlow c WHERE c.userId = :userId AND c.flowType = :flowType AND c.status = 'SUCCESS'")
    BigDecimal sumAmountByUserIdAndFlowType(@Param("userId") Long userId, @Param("flowType") String flowType);
} 