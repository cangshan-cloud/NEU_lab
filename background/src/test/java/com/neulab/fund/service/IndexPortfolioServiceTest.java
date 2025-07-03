package com.neulab.fund.service;

import com.neulab.fund.entity.IndexPortfolio;
import com.neulab.fund.repository.IndexPortfolioRepository;
import com.neulab.fund.service.impl.IndexPortfolioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IndexPortfolioServiceTest {

    @Mock
    private IndexPortfolioRepository indexPortfolioRepository;

    @InjectMocks
    private IndexPortfolioServiceImpl indexPortfolioService;

    private IndexPortfolio testPortfolio;

    @BeforeEach
    void setUp() {
        testPortfolio = new IndexPortfolio();
        testPortfolio.setId(1L);
        testPortfolio.setName("测试指数组合");
        testPortfolio.setDescription("测试描述");
    }

    @Test
    void testGetAllIndexPortfolios() {
        when(indexPortfolioRepository.findAll()).thenReturn(Arrays.asList(testPortfolio));
        
        List<IndexPortfolio> result = indexPortfolioService.getAllIndexPortfolios();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(indexPortfolioRepository).findAll();
    }

    @Test
    void testGetIndexPortfolioById() {
        when(indexPortfolioRepository.findById(1L)).thenReturn(Optional.of(testPortfolio));
        
        IndexPortfolio result = indexPortfolioService.getIndexPortfolioById(1L);
        
        assertNotNull(result);
        assertEquals(testPortfolio, result);
        verify(indexPortfolioRepository).findById(1L);
    }

    @Test
    void testCreateIndexPortfolio() {
        when(indexPortfolioRepository.save(any(IndexPortfolio.class))).thenReturn(testPortfolio);
        
        IndexPortfolio result = indexPortfolioService.createIndexPortfolio(testPortfolio);
        
        assertNotNull(result);
        assertEquals(testPortfolio, result);
        verify(indexPortfolioRepository).save(testPortfolio);
    }
} 