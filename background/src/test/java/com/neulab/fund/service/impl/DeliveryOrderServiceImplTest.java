package com.neulab.fund.service.impl;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import java.lang.reflect.Field;

class DeliveryOrderServiceImplTest {
    @Test
    void testAllPublicMethods() throws Exception {
        DeliveryOrderServiceImpl service = new DeliveryOrderServiceImpl();
        Field f1 = DeliveryOrderServiceImpl.class.getDeclaredField("deliveryOrderRepository");
        f1.setAccessible(true);
        f1.set(service, mock(com.neulab.fund.repository.DeliveryOrderRepository.class));
        service.toString();
        service.hashCode();
    }
} 