package com.neulab.fund.service;

import com.neulab.fund.entity.StrategyBacktest;
import com.neulab.fund.repository.StrategyBacktestRepository;
import com.neulab.fund.service.impl.StrategyBacktestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StrategyBacktestServiceTest {

    @Mock
    private StrategyBacktestRepository strategyBacktestRepository;

    @InjectMocks
    private StrategyBacktestServiceImpl strategyBacktestService;

    private StrategyBacktest testBacktest;

    @BeforeEach
    void setUp() {
        testBacktest = new StrategyBacktest();
        testBacktest.setId(1L);
        testBacktest.setStrategyId(1L);
        testBacktest.setStartDate(LocalDate.now().minusDays(30));
        testBacktest.setEndDate(LocalDate.now());
        testBacktest.setTotalReturn(0.15);
        testBacktest.setAnnualReturn(0.18);
        testBacktest.setMaxDrawdown(0.05);
        testBacktest.setSharpeRatio(1.2);
    }

    @Test
    void testGetAllBacktests() {
        when(strategyBacktestRepository.findAll()).thenReturn(Arrays.asList(testBacktest));
        List<StrategyBacktest> result = strategyBacktestService.getAllBacktests();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(strategyBacktestRepository).findAll();
    }

    @Test
    void testGetBacktestById() {
        when(strategyBacktestRepository.findById(1L)).thenReturn(java.util.Optional.of(testBacktest));
        StrategyBacktest result = strategyBacktestService.getBacktestById(1L);
        assertNotNull(result);
        assertEquals(testBacktest, result);
        verify(strategyBacktestRepository).findById(1L);
    }

    @Test
    void testCreateBacktest() {
        when(strategyBacktestRepository.save(any(StrategyBacktest.class))).thenReturn(testBacktest);
        StrategyBacktest result = strategyBacktestService.createBacktest(testBacktest);
        assertNotNull(result);
        assertEquals(testBacktest, result);
        verify(strategyBacktestRepository).save(testBacktest);
    }

    // 若有空置方法，补充如下：
    // @Test
    // public void testSomeBusinessMethod() {
    //     strategyBacktestService.someBusinessMethod(null);
    // }
} 