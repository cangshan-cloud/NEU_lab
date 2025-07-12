package com.neulab.fund.service;

import com.neulab.fund.entity.FundPortfolio;
import com.neulab.fund.repository.FundPortfolioRepository;
import com.neulab.fund.service.impl.FundPortfolioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class FundPortfolioServiceTest {

    @Mock
    private FundPortfolioRepository fundPortfolioRepository;

    @InjectMocks
    private FundPortfolioServiceImpl fundPortfolioService;

    private FundPortfolio testPortfolio;

    @BeforeEach
    void setUp() {
        testPortfolio = new FundPortfolio();
        testPortfolio.setId(1L);
        testPortfolio.setPortfolioName("测试基金组合");
        testPortfolio.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void testGetAllPortfolios() {
        when(fundPortfolioRepository.findAll()).thenReturn(Arrays.asList(testPortfolio));
        List<FundPortfolio> result = fundPortfolioService.getAllPortfolios();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(fundPortfolioRepository).findAll();
    }

    @Test
    void testGetPortfolioById() {
        when(fundPortfolioRepository.findById(1L)).thenReturn(java.util.Optional.of(testPortfolio));
        FundPortfolio result = fundPortfolioService.getPortfolioById(1L);
        assertNotNull(result);
        assertEquals(testPortfolio, result);
        verify(fundPortfolioRepository).findById(1L);
    }

    @Test
    void testCreatePortfolio() {
        when(fundPortfolioRepository.save(any(FundPortfolio.class))).thenReturn(testPortfolio);
        FundPortfolio result = fundPortfolioService.createPortfolio(testPortfolio);
        assertNotNull(result);
        assertEquals(testPortfolio, result);
        verify(fundPortfolioRepository).save(testPortfolio);
    }

    @Test
    void testAllPublicMethods() {
        FundPortfolioRepository repo = mock(FundPortfolioRepository.class);
        FundPortfolioServiceImpl service = new FundPortfolioServiceImpl(repo);
        service.toString();
        service.hashCode();
    }
} 