package com.neulab.fund.service;

import com.neulab.fund.entity.UserPosition;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 用户持仓Service接口
 */
public interface UserPositionService {
    
    /**
     * 创建用户持仓
     */
    UserPosition createPosition(UserPosition position);
    
    /**
     * 根据ID查询持仓
     */
    Optional<UserPosition> findById(Long id);
    
    /**
     * 根据用户ID和产品ID查询持仓
     */
    Optional<UserPosition> findByUserIdAndProductId(Long userId, Long productId);
    
    /**
     * 查询所有持仓
     */
    List<UserPosition> findAll();
    
    /**
     * 根据用户ID查询持仓列表
     */
    List<UserPosition> findByUserId(Long userId);
    
    /**
     * 根据产品ID查询持仓列表
     */
    List<UserPosition> findByProductId(Long productId);
    
    /**
     * 更新持仓信息
     */
    UserPosition updatePosition(UserPosition position);
    
    /**
     * 更新持仓份额
     */
    UserPosition updateShares(Long userId, Long productId, BigDecimal shares);
    
    /**
     * 更新持仓市值
     */
    UserPosition updateMarketValue(Long userId, Long productId, BigDecimal marketValue);
    
    /**
     * 删除持仓
     */
    void deletePosition(Long id);
    
    /**
     * 查询有持仓的用户数量
     */
    long countDistinctUsers();
    
    /**
     * 查询产品持仓用户数量
     */
    long countByProductId(Long productId);
    
    /**
     * 查询用户持仓总市值
     */
    BigDecimal sumMarketValueByUserId(Long userId);
    
    /**
     * 查询用户持仓总成本
     */
    BigDecimal sumCostByUserId(Long userId);
    
    /**
     * 查询用户总浮动盈亏
     */
    BigDecimal sumProfitLossByUserId(Long userId);
} 