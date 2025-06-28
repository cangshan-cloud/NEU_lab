package com.neulab.fund.service.impl;

import com.neulab.fund.entity.TradeRecord;
import com.neulab.fund.repository.TradeRecordRepository;
import com.neulab.fund.service.TradeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 交易记录Service实现类
 */
@Service
public class TradeRecordServiceImpl implements TradeRecordService {
    
    @Autowired
    private TradeRecordRepository tradeRecordRepository;
    
    @Override
    public TradeRecord createRecord(TradeRecord record) {
        // 生成记录编号
        record.setRecordNo(generateRecordNo());
        record.setTradeTime(LocalDateTime.now());
        record.setCreatedAt(LocalDateTime.now());
        return tradeRecordRepository.save(record);
    }
    
    @Override
    public Optional<TradeRecord> findById(Long id) {
        return tradeRecordRepository.findById(id);
    }
    
    @Override
    public Optional<TradeRecord> findByRecordNo(String recordNo) {
        return tradeRecordRepository.findByRecordNo(recordNo);
    }
    
    @Override
    public List<TradeRecord> findAll() {
        return tradeRecordRepository.findAll();
    }
    
    @Override
    public List<TradeRecord> findByOrderId(Long orderId) {
        return tradeRecordRepository.findByOrderIdOrderByTradeTimeDesc(orderId);
    }
    
    @Override
    public List<TradeRecord> findByUserId(Long userId) {
        return tradeRecordRepository.findByUserIdOrderByTradeTimeDesc(userId);
    }
    
    @Override
    public List<TradeRecord> findByUserIdAndTradeType(Long userId, String tradeType) {
        return tradeRecordRepository.findByUserIdAndTradeTypeOrderByTradeTimeDesc(userId, tradeType);
    }
    
    @Override
    public List<TradeRecord> findByProductId(Long productId) {
        return tradeRecordRepository.findByProductIdOrderByTradeTimeDesc(productId);
    }
    
    @Override
    public List<TradeRecord> findByStatus(String status) {
        return tradeRecordRepository.findByStatusOrderByTradeTimeDesc(status);
    }
    
    @Override
    public List<TradeRecord> findByTradeTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return tradeRecordRepository.findByTradeTimeBetween(startTime, endTime);
    }
    
    @Override
    public TradeRecord updateRecord(TradeRecord record) {
        return tradeRecordRepository.save(record);
    }
    
    @Override
    public void deleteRecord(Long id) {
        tradeRecordRepository.deleteById(id);
    }
    
    @Override
    public long countByUserId(Long userId) {
        return tradeRecordRepository.countByUserId(userId);
    }
    
    @Override
    public long countSuccessByUserId(Long userId) {
        return tradeRecordRepository.countSuccessByUserId(userId);
    }
    
    /**
     * 生成记录编号
     */
    private String generateRecordNo() {
        return "TR" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
} 