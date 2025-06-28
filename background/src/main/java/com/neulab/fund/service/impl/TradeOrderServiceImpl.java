package com.neulab.fund.service.impl;

import com.neulab.fund.entity.TradeOrder;
import com.neulab.fund.repository.TradeOrderRepository;
import com.neulab.fund.service.TradeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 交易订单Service实现类
 */
@Service
public class TradeOrderServiceImpl implements TradeOrderService {
    
    @Autowired
    private TradeOrderRepository tradeOrderRepository;
    
    @Override
    public TradeOrder createOrder(TradeOrder order) {
        // 生成订单编号
        order.setOrderNo(generateOrderNo());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        return tradeOrderRepository.save(order);
    }
    
    @Override
    public Optional<TradeOrder> findById(Long id) {
        return tradeOrderRepository.findById(id);
    }
    
    @Override
    public Optional<TradeOrder> findByOrderNo(String orderNo) {
        return tradeOrderRepository.findByOrderNo(orderNo);
    }
    
    @Override
    public List<TradeOrder> findAll() {
        return tradeOrderRepository.findAll();
    }
    
    @Override
    public List<TradeOrder> findByUserId(Long userId) {
        return tradeOrderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
    
    @Override
    public List<TradeOrder> findByUserIdAndStatus(Long userId, String status) {
        return tradeOrderRepository.findByUserIdAndStatusOrderByCreatedAtDesc(userId, status);
    }
    
    @Override
    public List<TradeOrder> findByProductId(Long productId) {
        return tradeOrderRepository.findByProductIdOrderByCreatedAtDesc(productId);
    }
    
    @Override
    public List<TradeOrder> findByStatus(String status) {
        return tradeOrderRepository.findByStatusOrderByCreatedAtDesc(status);
    }
    
    @Override
    public TradeOrder updateOrderStatus(Long orderId, String status) {
        Optional<TradeOrder> optional = tradeOrderRepository.findById(orderId);
        if (optional.isPresent()) {
            TradeOrder order = optional.get();
            order.setStatus(status);
            order.setUpdatedAt(LocalDateTime.now());
            if ("SUCCESS".equals(status) || "FAILED".equals(status)) {
                order.setProcessedAt(LocalDateTime.now());
            }
            return tradeOrderRepository.save(order);
        }
        return null;
    }
    
    @Override
    public TradeOrder cancelOrder(Long orderId) {
        return updateOrderStatus(orderId, "CANCELLED");
    }
    
    @Override
    public void deleteOrder(Long id) {
        tradeOrderRepository.deleteById(id);
    }
    
    @Override
    public long countByUserId(Long userId) {
        return tradeOrderRepository.countByUserId(userId);
    }
    
    @Override
    public long countSuccessByUserId(Long userId) {
        return tradeOrderRepository.countSuccessByUserId(userId);
    }
    
    /**
     * 生成订单编号
     */
    private String generateOrderNo() {
        return "TO" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
} 