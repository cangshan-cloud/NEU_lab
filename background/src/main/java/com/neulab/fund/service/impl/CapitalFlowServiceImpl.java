package com.neulab.fund.service.impl;

import com.neulab.fund.entity.CapitalFlow;
import com.neulab.fund.repository.CapitalFlowRepository;
import com.neulab.fund.service.CapitalFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 资金流水Service实现类
 */
@Service
public class CapitalFlowServiceImpl implements CapitalFlowService {
    
    @Autowired
    private CapitalFlowRepository capitalFlowRepository;
    
    @Override
    public CapitalFlow createFlow(CapitalFlow flow) {
        // 生成流水编号
        flow.setFlowNo(generateFlowNo());
        flow.setFlowTime(LocalDateTime.now());
        flow.setCreatedAt(LocalDateTime.now());
        return capitalFlowRepository.save(flow);
    }
    
    @Override
    public Optional<CapitalFlow> findById(Long id) {
        return capitalFlowRepository.findById(id);
    }
    
    @Override
    public Optional<CapitalFlow> findByFlowNo(String flowNo) {
        return capitalFlowRepository.findByFlowNo(flowNo);
    }
    
    @Override
    public List<CapitalFlow> findAll() {
        return capitalFlowRepository.findAll();
    }
    
    @Override
    public List<CapitalFlow> findByUserId(Long userId) {
        return capitalFlowRepository.findByUserIdOrderByFlowTimeDesc(userId);
    }
    
    @Override
    public List<CapitalFlow> findByUserIdAndFlowType(Long userId, String flowType) {
        return capitalFlowRepository.findByUserIdAndFlowTypeOrderByFlowTimeDesc(userId, flowType);
    }
    
    @Override
    public List<CapitalFlow> findByUserIdAndStatus(Long userId, String status) {
        return capitalFlowRepository.findByUserIdAndStatusOrderByFlowTimeDesc(userId, status);
    }
    
    @Override
    public List<CapitalFlow> findByOrderId(Long orderId) {
        return capitalFlowRepository.findByOrderIdOrderByFlowTimeDesc(orderId);
    }
    
    @Override
    public List<CapitalFlow> findByProductId(Long productId) {
        return capitalFlowRepository.findByProductIdOrderByFlowTimeDesc(productId);
    }
    
    @Override
    public List<CapitalFlow> findByFlowTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return capitalFlowRepository.findByFlowTimeBetween(startTime, endTime);
    }
    
    @Override
    public CapitalFlow updateFlow(CapitalFlow flow) {
        return capitalFlowRepository.save(flow);
    }
    
    @Override
    public void deleteFlow(Long id) {
        capitalFlowRepository.deleteById(id);
    }
    
    @Override
    public long countByUserId(Long userId) {
        return capitalFlowRepository.countByUserId(userId);
    }
    
    @Override
    public long countSuccessByUserId(Long userId) {
        return capitalFlowRepository.countSuccessByUserId(userId);
    }
    
    @Override
    public BigDecimal sumAmountByUserId(Long userId) {
        return capitalFlowRepository.sumAmountByUserId(userId);
    }
    
    @Override
    public BigDecimal sumAmountByUserIdAndFlowType(Long userId, String flowType) {
        return capitalFlowRepository.sumAmountByUserIdAndFlowType(userId, flowType);
    }
    
    /**
     * 生成流水编号
     */
    private String generateFlowNo() {
        return "CF" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
} 