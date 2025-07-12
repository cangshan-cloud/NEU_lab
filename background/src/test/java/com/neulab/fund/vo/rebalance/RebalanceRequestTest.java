package com.neulab.fund.vo.rebalance;

import org.junit.jupiter.api.Test;

class RebalanceRequestTest {
    @Test
    void testAllPublicMethods() {
        RebalanceRequest obj = new RebalanceRequest();
        obj.setPortfolioId(1L);
        obj.setItems(null);
        obj.getPortfolioId();
        obj.getItems();
        obj.toString();
        obj.hashCode();
    }
} 