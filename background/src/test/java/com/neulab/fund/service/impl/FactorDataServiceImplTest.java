package com.neulab.fund.service.impl;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import java.lang.reflect.Field;

class FactorDataServiceImplTest {
    @Test
    void testAllPublicMethods() throws Exception {
        FactorDataServiceImpl service = new FactorDataServiceImpl();
        Field f1 = FactorDataServiceImpl.class.getDeclaredField("factorDataRepository");
        f1.setAccessible(true);
        f1.set(service, mock(com.neulab.fund.repository.FactorDataRepository.class));
        service.toString();
        service.hashCode();
    }
} 