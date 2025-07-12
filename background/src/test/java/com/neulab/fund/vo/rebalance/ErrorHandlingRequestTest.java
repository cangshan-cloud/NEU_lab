package com.neulab.fund.vo.rebalance;

import org.junit.jupiter.api.Test;

class ErrorHandlingRequestTest {
    @Test
    void testAllPublicMethods() {
        ErrorHandlingRequest obj = new ErrorHandlingRequest();
        obj.setDeliveryOrderId(1L);
        obj.setNewProductId(2L);
        obj.getDeliveryOrderId();
        obj.getNewProductId();
        obj.toString();
        obj.hashCode();
    }
} 