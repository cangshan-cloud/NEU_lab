package com.neulab.fund.vo.rebalance;

/**
 * 差错处理请求DTO
 */
public class ErrorHandlingRequest {
    private Long deliveryOrderId; // 失败交割单ID
    private Long newProductId;    // 替代标的产品ID
    // getter/setter
    public Long getDeliveryOrderId() { return deliveryOrderId; }
    public void setDeliveryOrderId(Long deliveryOrderId) { this.deliveryOrderId = deliveryOrderId; }
    public Long getNewProductId() { return newProductId; }
    public void setNewProductId(Long newProductId) { this.newProductId = newProductId; }
} 