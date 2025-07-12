package com.neulab.fund.service;

import com.neulab.fund.entity.TimingPortfolio;
import com.neulab.fund.repository.TimingPortfolioRepository;
import com.neulab.fund.service.impl.TimingPortfolioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TimingPortfolioServiceTest {
    @Mock
    private TimingPortfolioRepository timingPortfolioRepository;
    @InjectMocks
    private TimingPortfolioServiceImpl timingPortfolioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTimingPortfolio() {
        // Given
        TimingPortfolio testPortfolio = new TimingPortfolio();
        testPortfolio.setPortfolioName("择时组合");
        testPortfolio.setPortfolioCode("TIMING001");
        testPortfolio.setTimingStrategy("动量择时");
        testPortfolio.setStatus("ACTIVE");

        TimingPortfolio savedPortfolio = new TimingPortfolio();
        savedPortfolio.setId(1L);
        savedPortfolio.setPortfolioName("择时组合");
        savedPortfolio.setPortfolioCode("TIMING001");
        savedPortfolio.setTimingStrategy("动量择时");
        savedPortfolio.setStatus("ACTIVE");

        when(timingPortfolioRepository.save(any(TimingPortfolio.class))).thenReturn(savedPortfolio);

        // When
        TimingPortfolio result = timingPortfolioService.createTimingPortfolio(testPortfolio);

        // Then
        assertNotNull(result);
        assertEquals("择时组合", result.getPortfolioName());
        assertEquals("TIMING001", result.getPortfolioCode());
        assertEquals("动量择时", result.getTimingStrategy());
        verify(timingPortfolioRepository).save(any(TimingPortfolio.class));
    }

    @Test
    void testGetTimingPortfolioById() {
        // Given
        Long portfolioId = 1L;
        TimingPortfolio portfolio = new TimingPortfolio();
        portfolio.setId(portfolioId);
        portfolio.setPortfolioName("动量择时组合");
        portfolio.setPortfolioCode("TIMING001");
        portfolio.setTimingStrategy("动量择时");

        when(timingPortfolioRepository.findById(portfolioId)).thenReturn(Optional.of(portfolio));

        // When
        TimingPortfolio result = timingPortfolioService.getTimingPortfolioById(portfolioId);

        // Then
        assertNotNull(result);
        assertEquals(portfolioId, result.getId());
        assertEquals("动量择时组合", result.getPortfolioName());
    }

    @Test
    void testGetTimingPortfolioByIdNotFound() {
        // Given
        Long portfolioId = 999L;
        when(timingPortfolioRepository.findById(portfolioId)).thenReturn(Optional.empty());

        // When
        TimingPortfolio result = timingPortfolioService.getTimingPortfolioById(portfolioId);

        // Then
        assertNull(result);
    }

    @Test
    void testGetAllTimingPortfolios() {
        // Given
        TimingPortfolio portfolio1 = new TimingPortfolio();
        portfolio1.setId(1L);
        portfolio1.setPortfolioName("动量择时组合");
        portfolio1.setPortfolioCode("TIMING001");
        portfolio1.setTimingStrategy("动量择时");

        TimingPortfolio portfolio2 = new TimingPortfolio();
        portfolio2.setId(2L);
        portfolio2.setPortfolioName("均值回归择时组合");
        portfolio2.setPortfolioCode("TIMING002");
        portfolio2.setTimingStrategy("均值回归择时");

        List<TimingPortfolio> portfolios = Arrays.asList(portfolio1, portfolio2);
        when(timingPortfolioRepository.findAll()).thenReturn(portfolios);

        // When
        List<TimingPortfolio> result = timingPortfolioService.getAllTimingPortfolios();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("动量择时组合", result.get(0).getPortfolioName());
        assertEquals("均值回归择时组合", result.get(1).getPortfolioName());
    }
} 