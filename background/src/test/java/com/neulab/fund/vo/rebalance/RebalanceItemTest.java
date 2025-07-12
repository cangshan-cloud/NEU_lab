package com.neulab.fund.vo.rebalance;

import org.junit.jupiter.api.Test;

class RebalanceItemTest {
    @Test
    void testAllPublicMethods() {
        RebalanceItem obj = new RebalanceItem();
        obj.setProductId(1L);
        obj.setWeight(null);
        obj.getProductId();
        obj.getWeight();
        obj.toString();
        obj.hashCode();
    }
} 