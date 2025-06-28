package com.neulab.fund.repository;

import com.neulab.fund.entity.UserPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 用户持仓Repository接口
 */
@Repository
public interface UserPositionRepository extends JpaRepository<UserPosition, Long> {
    
    /**
     * 根据用户ID和产品ID查询持仓
     */
    Optional<UserPosition> findByUserIdAndProductId(Long userId, Long productId);
    
    /**
     * 根据用户ID查询持仓列表
     */
    List<UserPosition> findByUserIdOrderByUpdatedAtDesc(Long userId);
    
    /**
     * 根据产品ID查询持仓列表
     */
    List<UserPosition> findByProductIdOrderBySharesDesc(Long productId);
    
    /**
     * 查询有持仓的用户数量
     */
    @Query("SELECT COUNT(DISTINCT u.userId) FROM UserPosition u")
    long countDistinctUsers();
    
    /**
     * 查询产品持仓用户数量
     */
    @Query("SELECT COUNT(u) FROM UserPosition u WHERE u.productId = :productId")
    long countByProductId(@Param("productId") Long productId);
    
    /**
     * 查询用户持仓总市值
     */
    @Query("SELECT COALESCE(SUM(u.marketValue), 0) FROM UserPosition u WHERE u.userId = :userId")
    BigDecimal sumMarketValueByUserId(@Param("userId") Long userId);
    
    /**
     * 查询用户持仓总成本
     */
    @Query("SELECT COALESCE(SUM(u.cost), 0) FROM UserPosition u WHERE u.userId = :userId")
    BigDecimal sumCostByUserId(@Param("userId") Long userId);
    
    /**
     * 查询用户总浮动盈亏
     */
    @Query("SELECT COALESCE(SUM(u.profitLoss), 0) FROM UserPosition u WHERE u.userId = :userId")
    BigDecimal sumProfitLossByUserId(@Param("userId") Long userId);
} 