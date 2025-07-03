package com.neulab.fund.service;

import com.neulab.fund.entity.TimingPortfolio;
import com.neulab.fund.repository.TimingPortfolioRepository;
import com.neulab.fund.service.impl.TimingPortfolioServiceImpl;
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

@ExtendWith(MockitoExtension.class)
class TimingPortfolioServiceTest {

    @Mock
    private TimingPortfolioRepository timingPortfolioRepository;

    @InjectMocks
    private TimingPortfolioServiceImpl timingPortfolioService;

    private TimingPortfolio testPortfolio;

    @BeforeEach
    void setUp() {
        testPortfolio = new TimingPortfolio();
        testPortfolio.setId(1L);
        testPortfolio.setName("择时组合");
        testPortfolio.setDescription("基于择时策略的组合");
        testPortfolio.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void testGetAllTimingPortfolios() {
        when(timingPortfolioRepository.findAll()).thenReturn(Arrays.asList(testPortfolio));
        List<TimingPortfolio> result = timingPortfolioService.getAllTimingPortfolios();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(timingPortfolioRepository).findAll();
    }

    @Test
    void testGetTimingPortfolioById() {
        when(timingPortfolioRepository.findById(1L)).thenReturn(java.util.Optional.of(testPortfolio));
        TimingPortfolio result = timingPortfolioService.getTimingPortfolioById(1L);
        assertNotNull(result);
        assertEquals(testPortfolio, result);
        verify(timingPortfolioRepository).findById(1L);
    }

    @Test
    void testCreateTimingPortfolio() {
        when(timingPortfolioRepository.save(any(TimingPortfolio.class))).thenReturn(testPortfolio);
        TimingPortfolio result = timingPortfolioService.createTimingPortfolio(testPortfolio);
        assertNotNull(result);
        assertEquals(testPortfolio, result);
        verify(timingPortfolioRepository).save(testPortfolio);
    }
} 