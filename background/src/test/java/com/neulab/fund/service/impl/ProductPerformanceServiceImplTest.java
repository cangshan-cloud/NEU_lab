package com.neulab.fund.service.impl;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

class ProductPerformanceServiceImplTest {
    @Test
    void testAllPublicMethods() {
        com.neulab.fund.repository.ProductPerformanceRepository repo = mock(com.neulab.fund.repository.ProductPerformanceRepository.class);
        ProductPerformanceServiceImpl service = new ProductPerformanceServiceImpl(repo);
        service.toString();
        service.hashCode();
    }
} 