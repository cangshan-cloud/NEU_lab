package com.neulab.fund.vo.rebalance;

import org.junit.jupiter.api.Test;

class AccountRebalanceRequestTest {
    @Test
    void testAllPublicMethods() {
        AccountRebalanceRequest obj = new AccountRebalanceRequest();
        obj.setUserId(1L);
        obj.setItems(null);
        obj.getUserId();
        obj.getItems();
        obj.toString();
        obj.hashCode();
    }
} 