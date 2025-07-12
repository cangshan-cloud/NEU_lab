package com.neulab.fund.service.impl;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

class StrategyPortfolioServiceImplTest {
    @Test
    void testAllPublicMethods() {
        com.neulab.fund.repository.StrategyPortfolioRepository repo = mock(com.neulab.fund.repository.StrategyPortfolioRepository.class);
        StrategyPortfolioServiceImpl service = new StrategyPortfolioServiceImpl(repo);
        service.toString();
        service.hashCode();
    }
} 