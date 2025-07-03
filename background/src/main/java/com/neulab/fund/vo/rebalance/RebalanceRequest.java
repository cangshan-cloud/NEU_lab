package com.neulab.fund.vo.rebalance;

import java.util.List;

/**
 * 组合调仓请求DTO
 */
public class RebalanceRequest {
    private Long portfolioId; // 组合ID
    private List<RebalanceItem> items; // 新组合配置
    // getter/setter
    public Long getPortfolioId() { return portfolioId; }
    public void setPortfolioId(Long portfolioId) { this.portfolioId = portfolioId; }
    public List<RebalanceItem> getItems() { return items; }
    public void setItems(List<RebalanceItem> items) { this.items = items; }
} 