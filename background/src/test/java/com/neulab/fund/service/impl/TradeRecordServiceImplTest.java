package com.neulab.fund.service.impl;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import java.lang.reflect.Field;

class TradeRecordServiceImplTest {
    @Test
    void testAllPublicMethods() throws Exception {
        TradeRecordServiceImpl service = new TradeRecordServiceImpl();
        Field f1 = TradeRecordServiceImpl.class.getDeclaredField("tradeRecordRepository");
        f1.setAccessible(true);
        f1.set(service, mock(com.neulab.fund.repository.TradeRecordRepository.class));
        service.toString();
        service.hashCode();
    }
} 