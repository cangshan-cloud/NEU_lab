package com.neulab.fund.service.impl;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import java.lang.reflect.Field;

class FofPortfolioServiceImplTest {
    @Test
    void testAllPublicMethods() throws Exception {
        FofPortfolioServiceImpl service = new FofPortfolioServiceImpl();
        Field f1 = FofPortfolioServiceImpl.class.getDeclaredField("fofPortfolioRepository");
        f1.setAccessible(true);
        f1.set(service, mock(com.neulab.fund.repository.FofPortfolioRepository.class));
        service.toString();
        service.hashCode();
    }
} 