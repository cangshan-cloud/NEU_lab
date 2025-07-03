package com.neulab.fund.service;

import com.neulab.fund.entity.TradeOrder;
import com.neulab.fund.vo.rebalance.RebalanceRequest;
import com.neulab.fund.vo.rebalance.ErrorHandlingRequest;
import com.neulab.fund.vo.rebalance.AccountRebalanceRequest;
import java.util.List;
import java.util.Optional;

/**
 * 交易订单Service接口
 */
public interface TradeOrderService {
    
    /**
     * 创建交易订单
     */
    TradeOrder createOrder(TradeOrder order);
    
    /**
     * 根据ID查询订单
     */
    Optional<TradeOrder> findById(Long id);
    
    /**
     * 根据订单编号查询订单
     */
    Optional<TradeOrder> findByOrderNo(String orderNo);
    
    /**
     * 查询所有订单
     */
    List<TradeOrder> findAll();
    
    /**
     * 根据用户ID查询订单列表
     */
    List<TradeOrder> findByUserId(Long userId);
    
    /**
     * 根据用户ID和状态查询订单列表
     */
    List<TradeOrder> findByUserIdAndStatus(Long userId, String status);
    
    /**
     * 根据产品ID查询订单列表
     */
    List<TradeOrder> findByProductId(Long productId);
    
    /**
     * 根据状态查询订单列表
     */
    List<TradeOrder> findByStatus(String status);
    
    /**
     * 更新订单状态
     */
    TradeOrder updateOrderStatus(Long orderId, String status);
    
    /**
     * 取消订单
     */
    TradeOrder cancelOrder(Long orderId);
    
    /**
     * 删除订单
     */
    void deleteOrder(Long id);
    
    /**
     * 统计用户订单数量
     */
    long countByUserId(Long userId);
    
    /**
     * 统计用户成功订单数量
     */
    long countSuccessByUserId(Long userId);
    
    /**
     * 组合调仓：为持有指定组合的所有用户批量生成调仓交易单
     */
    List<TradeOrder> rebalancePortfolio(RebalanceRequest request);

    /**
     * 差错处理：为失败交割单指定替代标的并重新生成交易单
     */
    void handleError(ErrorHandlingRequest request);

    /**
     * 账户调仓：为指定客户单独调仓，生成调仓交易单
     */
    List<TradeOrder> accountRebalance(AccountRebalanceRequest request);

    /**
     * 批量下发交易单
     */
    void batchSubmit(List<Long> orderIds);

    /**
     * 批量驳回交易单
     */
    void batchReject(List<Long> orderIds);
} 