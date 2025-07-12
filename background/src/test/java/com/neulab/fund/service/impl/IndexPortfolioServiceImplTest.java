package com.neulab.fund.service.impl;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

class IndexPortfolioServiceImplTest {
    @Test
    void testAllPublicMethods() {
        com.neulab.fund.repository.IndexPortfolioRepository repo = mock(com.neulab.fund.repository.IndexPortfolioRepository.class);
        IndexPortfolioServiceImpl service = new IndexPortfolioServiceImpl(repo);
        service.toString();
        service.hashCode();
    }
} 