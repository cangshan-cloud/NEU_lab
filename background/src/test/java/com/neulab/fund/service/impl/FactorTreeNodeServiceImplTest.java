package com.neulab.fund.service.impl;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import java.lang.reflect.Field;

class FactorTreeNodeServiceImplTest {
    @Test
    void testAllPublicMethods() throws Exception {
        FactorTreeNodeServiceImpl service = new FactorTreeNodeServiceImpl();
        Field f1 = FactorTreeNodeServiceImpl.class.getDeclaredField("factorTreeNodeRepository");
        f1.setAccessible(true);
        f1.set(service, mock(com.neulab.fund.repository.FactorTreeNodeRepository.class));
        service.toString();
        service.hashCode();
    }
} 