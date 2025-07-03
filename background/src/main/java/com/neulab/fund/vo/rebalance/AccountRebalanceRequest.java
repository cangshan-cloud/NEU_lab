package com.neulab.fund.vo.rebalance;

import java.util.List;

/**
 * 账户调仓请求DTO
 */
public class AccountRebalanceRequest {
    private Long userId; // 客户ID
    private List<RebalanceItem> items; // 新组合配置
    // getter/setter
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public List<RebalanceItem> getItems() { return items; }
    public void setItems(List<RebalanceItem> items) { this.items = items; }
} 