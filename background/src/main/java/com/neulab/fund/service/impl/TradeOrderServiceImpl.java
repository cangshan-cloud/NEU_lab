package com.neulab.fund.service.impl;

import com.neulab.fund.entity.TradeOrder;
import com.neulab.fund.entity.UserPosition;
import com.neulab.fund.entity.DeliveryOrder;
import com.neulab.fund.entity.TradeError;
import com.neulab.fund.repository.TradeOrderRepository;
import com.neulab.fund.repository.UserPositionRepository;
import com.neulab.fund.repository.DeliveryOrderRepository;
import com.neulab.fund.repository.TradeErrorRepository;
import com.neulab.fund.service.TradeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.math.BigDecimal;
import java.util.ArrayList;
import com.neulab.fund.vo.rebalance.RebalanceRequest;
import com.neulab.fund.vo.rebalance.ErrorHandlingRequest;
import com.neulab.fund.vo.rebalance.AccountRebalanceRequest;
import com.neulab.fund.vo.rebalance.RebalanceItem;

/**
 * 交易订单Service实现类
 */
@Service
public class TradeOrderServiceImpl implements TradeOrderService {
    
    @Autowired
    private TradeOrderRepository tradeOrderRepository;
    
    @Autowired
    private UserPositionRepository userPositionRepository;
    
    @Autowired
    private DeliveryOrderRepository deliveryOrderRepository;
    
    @Autowired
    private TradeErrorRepository tradeErrorRepository;
    
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
    
    @Override
    @Transactional
    public List<TradeOrder> rebalancePortfolio(RebalanceRequest request) {
        Long portfolioId = request.getPortfolioId();
        List<RebalanceItem> newItems = request.getItems();
        List<Long> userIds = userPositionRepository.findDistinctUserIdsByPortfolioId(portfolioId);
        List<TradeOrder> result = new ArrayList<>();
        for (Long userId : userIds) {
            List<UserPosition> positions = userPositionRepository.findByUserIdAndPortfolioId(userId, portfolioId);
            for (RebalanceItem item : newItems) {
                Long productId = item.getProductId();
                BigDecimal targetWeight = item.getWeight();
                // 假设总市值为所有持仓市值之和
                BigDecimal totalMarketValue = positions.stream().map(p -> p.getMarketValue() == null ? BigDecimal.ZERO : p.getMarketValue()).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal targetMarketValue = totalMarketValue.multiply(targetWeight);
                // 查找当前持仓
                UserPosition current = positions.stream().filter(p -> p.getProductId().equals(productId)).findFirst().orElse(null);
                BigDecimal currentMarketValue = current != null && current.getMarketValue() != null ? current.getMarketValue() : BigDecimal.ZERO;
                BigDecimal diff = targetMarketValue.subtract(currentMarketValue);
                if (diff.abs().compareTo(new BigDecimal("1")) < 0) continue; // 差异小于1元忽略
                TradeOrder order = new TradeOrder();
                order.setUserId(userId);
                order.setProductId(productId);
                order.setTradeType(diff.compareTo(BigDecimal.ZERO) > 0 ? "BUY" : "SELL");
                order.setAmount(diff.abs());
                order.setShares(null); // 份额可后续补充
                order.setStatus("PENDING");
                order.setRemark("组合调仓自动生成");
                result.add(order);
            }
        }
        tradeOrderRepository.saveAll(result);
        return result;
    }

    @Override
    @Transactional
    public void handleError(ErrorHandlingRequest request) {
        Long deliveryOrderId = request.getDeliveryOrderId();
        Long newProductId = request.getNewProductId();
        DeliveryOrder deliveryOrder = deliveryOrderRepository.findById(deliveryOrderId).orElseThrow();
        TradeOrder newOrder = new TradeOrder();
        newOrder.setUserId(deliveryOrder.getUserId());
        newOrder.setProductId(newProductId);
        newOrder.setTradeType(deliveryOrder.getTradeType());
        newOrder.setAmount(deliveryOrder.getAmount());
        newOrder.setShares(deliveryOrder.getShares());
        newOrder.setStatus("PENDING");
        newOrder.setRemark("差错处理自动生成");
        tradeOrderRepository.save(newOrder);
        TradeError error = tradeErrorRepository.findByDeliveryOrderId(deliveryOrderId);
        if (error != null) {
            error.setStatus("RESOLVED");
            error.setRemark("已替换为产品ID: " + newProductId);
            tradeErrorRepository.save(error);
        }
    }

    @Override
    @Transactional
    public List<TradeOrder> accountRebalance(AccountRebalanceRequest request) {
        Long userId = request.getUserId();
        List<RebalanceItem> items = request.getItems();
        List<UserPosition> positions = userPositionRepository.findByUserId(userId);
        List<TradeOrder> result = new ArrayList<>();
        BigDecimal totalMarketValue = positions.stream().map(p -> p.getMarketValue() == null ? BigDecimal.ZERO : p.getMarketValue()).reduce(BigDecimal.ZERO, BigDecimal::add);
        for (RebalanceItem item : items) {
            Long productId = item.getProductId();
            BigDecimal targetWeight = item.getWeight();
            BigDecimal targetMarketValue = totalMarketValue.multiply(targetWeight);
            UserPosition current = positions.stream().filter(p -> p.getProductId().equals(productId)).findFirst().orElse(null);
            BigDecimal currentMarketValue = current != null && current.getMarketValue() != null ? current.getMarketValue() : BigDecimal.ZERO;
            BigDecimal diff = targetMarketValue.subtract(currentMarketValue);
            if (diff.abs().compareTo(new BigDecimal("1")) < 0) continue;
            TradeOrder order = new TradeOrder();
            order.setUserId(userId);
            order.setProductId(productId);
            order.setTradeType(diff.compareTo(BigDecimal.ZERO) > 0 ? "BUY" : "SELL");
            order.setAmount(diff.abs());
            order.setShares(null);
            order.setStatus("PENDING");
            order.setRemark("账户调仓自动生成");
            result.add(order);
        }
        tradeOrderRepository.saveAll(result);
        return result;
    }
    
    /**
     * 生成订单编号
     */
    private String generateOrderNo() {
        return "TO" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Override
    @Transactional
    public void batchSubmit(List<Long> orderIds) {
        List<TradeOrder> orders = tradeOrderRepository.findAllById(orderIds);
        for (TradeOrder order : orders) {
            order.setStatus("PROCESSING");
        }
        tradeOrderRepository.saveAll(orders);
    }

    @Override
    @Transactional
    public void batchReject(List<Long> orderIds) {
        List<TradeOrder> orders = tradeOrderRepository.findAllById(orderIds);
        for (TradeOrder order : orders) {
            order.setStatus("REJECTED");
        }
        tradeOrderRepository.saveAll(orders);
    }
} 