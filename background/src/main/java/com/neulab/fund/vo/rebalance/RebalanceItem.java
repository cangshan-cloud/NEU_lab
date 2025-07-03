package com.neulab.fund.vo.rebalance;

import java.math.BigDecimal;

/**
 * 调仓目标标的及权重
 */
public class RebalanceItem {
    private Long productId; // 标的产品ID
    private BigDecimal weight; // 权重
    // getter/setter
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }
} 