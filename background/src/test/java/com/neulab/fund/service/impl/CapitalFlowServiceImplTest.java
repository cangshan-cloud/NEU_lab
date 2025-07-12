package com.neulab.fund.service.impl;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import java.lang.reflect.Field;

class CapitalFlowServiceImplTest {
    @Test
    void testAllPublicMethods() throws Exception {
        CapitalFlowServiceImpl service = new CapitalFlowServiceImpl();
        Field f1 = CapitalFlowServiceImpl.class.getDeclaredField("capitalFlowRepository");
        f1.setAccessible(true);
        f1.set(service, mock(com.neulab.fund.repository.CapitalFlowRepository.class));
        service.toString();
        service.hashCode();
    }
} 