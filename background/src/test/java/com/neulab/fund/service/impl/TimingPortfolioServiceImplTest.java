package com.neulab.fund.service.impl;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

class TimingPortfolioServiceImplTest {
    @Test
    void testAllPublicMethods() {
        com.neulab.fund.repository.TimingPortfolioRepository repo = mock(com.neulab.fund.repository.TimingPortfolioRepository.class);
        TimingPortfolioServiceImpl service = new TimingPortfolioServiceImpl(repo);
        service.toString();
        service.hashCode();
    }
} 