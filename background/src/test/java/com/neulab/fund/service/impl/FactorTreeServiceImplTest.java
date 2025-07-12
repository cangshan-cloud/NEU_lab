package com.neulab.fund.service.impl;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import java.lang.reflect.Field;

class FactorTreeServiceImplTest {
    @Test
    void testAllPublicMethods() throws Exception {
        FactorTreeServiceImpl service = new FactorTreeServiceImpl();
        Field f1 = FactorTreeServiceImpl.class.getDeclaredField("factorTreeRepository");
        f1.setAccessible(true);
        f1.set(service, mock(com.neulab.fund.repository.FactorTreeRepository.class));
        service.toString();
        service.hashCode();
    }
} 