package com.neulab.fund.service;

import com.neulab.fund.entity.StrategyPortfolio;
import com.neulab.fund.repository.StrategyPortfolioRepository;
import com.neulab.fund.service.impl.StrategyPortfolioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StrategyPortfolioServiceTest {

    @Mock
    private StrategyPortfolioRepository strategyPortfolioRepository;

    @InjectMocks
    private StrategyPortfolioServiceImpl strategyPortfolioService;

    private StrategyPortfolio testPortfolio;

    @BeforeEach
    void setUp() {
        testPortfolio = new StrategyPortfolio();
        testPortfolio.setId(1L);
        testPortfolio.setStrategyId(1L);
        testPortfolio.setPortfolioId(1L);
        testPortfolio.setWeight(new BigDecimal("0.25"));
        testPortfolio.setStatus("ACTIVE");
    }

    @Test
    void testGetAllRelations() {
        when(strategyPortfolioRepository.findAll()).thenReturn(Arrays.asList(testPortfolio));
        
        List<StrategyPortfolio> result = strategyPortfolioService.getAllRelations();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(strategyPortfolioRepository).findAll();
    }

    @Test
    void testGetRelationById() {
        when(strategyPortfolioRepository.findById(1L)).thenReturn(Optional.of(testPortfolio));
        
        StrategyPortfolio result = strategyPortfolioService.getRelationById(1L);
        
        assertNotNull(result);
        assertEquals(testPortfolio, result);
        verify(strategyPortfolioRepository).findById(1L);
    }

    @Test
    void testCreateRelation() {
        when(strategyPortfolioRepository.save(any(StrategyPortfolio.class))).thenReturn(testPortfolio);
        
        StrategyPortfolio result = strategyPortfolioService.createRelation(testPortfolio);
        
        assertNotNull(result);
        assertEquals(testPortfolio, result);
        verify(strategyPortfolioRepository).save(testPortfolio);
    }

    // 若有空置方法，补充如下：
    // @Test
    // public void testSomeBusinessMethod() {
    //     strategyPortfolioService.someBusinessMethod(null);
    // }
} 