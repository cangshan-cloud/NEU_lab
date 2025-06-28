package com.neulab.fund.service.impl;

import com.neulab.fund.entity.UserPosition;
import com.neulab.fund.repository.UserPositionRepository;
import com.neulab.fund.service.UserPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 用户持仓Service实现类
 */
@Service
public class UserPositionServiceImpl implements UserPositionService {
    
    @Autowired
    private UserPositionRepository userPositionRepository;
    
    @Override
    public UserPosition createPosition(UserPosition position) {
        position.setCreatedAt(LocalDateTime.now());
        position.setUpdatedAt(LocalDateTime.now());
        return userPositionRepository.save(position);
    }
    
    @Override
    public Optional<UserPosition> findById(Long id) {
        return userPositionRepository.findById(id);
    }
    
    @Override
    public Optional<UserPosition> findByUserIdAndProductId(Long userId, Long productId) {
        return userPositionRepository.findByUserIdAndProductId(userId, productId);
    }
    
    @Override
    public List<UserPosition> findAll() {
        return userPositionRepository.findAll();
    }
    
    @Override
    public List<UserPosition> findByUserId(Long userId) {
        return userPositionRepository.findByUserIdOrderByUpdatedAtDesc(userId);
    }
    
    @Override
    public List<UserPosition> findByProductId(Long productId) {
        return userPositionRepository.findByProductIdOrderBySharesDesc(productId);
    }
    
    @Override
    public UserPosition updatePosition(UserPosition position) {
        position.setUpdatedAt(LocalDateTime.now());
        return userPositionRepository.save(position);
    }
    
    @Override
    public UserPosition updateShares(Long userId, Long productId, BigDecimal shares) {
        Optional<UserPosition> optional = userPositionRepository.findByUserIdAndProductId(userId, productId);
        if (optional.isPresent()) {
            UserPosition position = optional.get();
            position.setShares(shares);
            position.setUpdatedAt(LocalDateTime.now());
            return userPositionRepository.save(position);
        }
        return null;
    }
    
    @Override
    public UserPosition updateMarketValue(Long userId, Long productId, BigDecimal marketValue) {
        Optional<UserPosition> optional = userPositionRepository.findByUserIdAndProductId(userId, productId);
        if (optional.isPresent()) {
            UserPosition position = optional.get();
            position.setMarketValue(marketValue);
            
            // 计算浮动盈亏
            if (position.getCost() != null && marketValue != null) {
                BigDecimal profitLoss = marketValue.subtract(position.getCost());
                position.setProfitLoss(profitLoss);
                
                // 计算浮动盈亏率
                if (position.getCost().compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal profitLossRate = profitLoss.divide(position.getCost(), 4, BigDecimal.ROUND_HALF_UP)
                            .multiply(new BigDecimal("100"));
                    position.setProfitLossRate(profitLossRate);
                }
            }
            
            position.setUpdatedAt(LocalDateTime.now());
            return userPositionRepository.save(position);
        }
        return null;
    }
    
    @Override
    public void deletePosition(Long id) {
        userPositionRepository.deleteById(id);
    }
    
    @Override
    public long countDistinctUsers() {
        return userPositionRepository.countDistinctUsers();
    }
    
    @Override
    public long countByProductId(Long productId) {
        return userPositionRepository.countByProductId(productId);
    }
    
    @Override
    public BigDecimal sumMarketValueByUserId(Long userId) {
        return userPositionRepository.sumMarketValueByUserId(userId);
    }
    
    @Override
    public BigDecimal sumCostByUserId(Long userId) {
        return userPositionRepository.sumCostByUserId(userId);
    }
    
    @Override
    public BigDecimal sumProfitLossByUserId(Long userId) {
        return userPositionRepository.sumProfitLossByUserId(userId);
    }
} 