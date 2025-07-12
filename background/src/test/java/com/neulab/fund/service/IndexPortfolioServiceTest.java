package com.neulab.fund.service;

import com.neulab.fund.entity.IndexPortfolio;
import com.neulab.fund.repository.IndexPortfolioRepository;
import com.neulab.fund.service.impl.IndexPortfolioServiceImpl;
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

class IndexPortfolioServiceTest {
    @Mock
    private IndexPortfolioRepository indexPortfolioRepository;
    @InjectMocks
    private IndexPortfolioServiceImpl indexPortfolioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateIndexPortfolio() {
        // Given
        IndexPortfolio testPortfolio = new IndexPortfolio();
        testPortfolio.setPortfolioName("测试指数组合");
        testPortfolio.setPortfolioCode("INDEX001");
        testPortfolio.setIndexCode("000300");
        testPortfolio.setIndexName("沪深300");
        testPortfolio.setStatus("ACTIVE");

        IndexPortfolio savedPortfolio = new IndexPortfolio();
        savedPortfolio.setId(1L);
        savedPortfolio.setPortfolioName("测试指数组合");
        savedPortfolio.setPortfolioCode("INDEX001");
        savedPortfolio.setIndexCode("000300");
        savedPortfolio.setIndexName("沪深300");
        savedPortfolio.setStatus("ACTIVE");

        when(indexPortfolioRepository.save(any(IndexPortfolio.class))).thenReturn(savedPortfolio);

        // When
        IndexPortfolio result = indexPortfolioService.createIndexPortfolio(testPortfolio);

        // Then
        assertNotNull(result);
        assertEquals("测试指数组合", result.getPortfolioName());
        assertEquals("INDEX001", result.getPortfolioCode());
        assertEquals("000300", result.getIndexCode());
        verify(indexPortfolioRepository).save(any(IndexPortfolio.class));
    }

    @Test
    void testGetIndexPortfolioById() {
        // Given
        Long portfolioId = 1L;
        IndexPortfolio portfolio = new IndexPortfolio();
        portfolio.setId(portfolioId);
        portfolio.setPortfolioName("沪深300组合");
        portfolio.setPortfolioCode("INDEX001");
        portfolio.setIndexCode("000300");

        when(indexPortfolioRepository.findById(portfolioId)).thenReturn(Optional.of(portfolio));

        // When
        IndexPortfolio result = indexPortfolioService.getIndexPortfolioById(portfolioId);

        // Then
        assertNotNull(result);
        assertEquals(portfolioId, result.getId());
        assertEquals("沪深300组合", result.getPortfolioName());
    }

    @Test
    void testGetIndexPortfolioByIdNotFound() {
        // Given
        Long portfolioId = 999L;
        when(indexPortfolioRepository.findById(portfolioId)).thenReturn(Optional.empty());

        // When
        IndexPortfolio result = indexPortfolioService.getIndexPortfolioById(portfolioId);

        // Then
        assertNull(result);
    }

    @Test
    void testGetAllIndexPortfolios() {
        // Given
        IndexPortfolio portfolio1 = new IndexPortfolio();
        portfolio1.setId(1L);
        portfolio1.setPortfolioName("沪深300组合");
        portfolio1.setPortfolioCode("INDEX001");
        portfolio1.setIndexCode("000300");

        IndexPortfolio portfolio2 = new IndexPortfolio();
        portfolio2.setId(2L);
        portfolio2.setPortfolioName("中证500组合");
        portfolio2.setPortfolioCode("INDEX002");
        portfolio2.setIndexCode("000905");

        List<IndexPortfolio> portfolios = Arrays.asList(portfolio1, portfolio2);
        when(indexPortfolioRepository.findAll()).thenReturn(portfolios);

        // When
        List<IndexPortfolio> result = indexPortfolioService.getAllIndexPortfolios();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("沪深300组合", result.get(0).getPortfolioName());
        assertEquals("中证500组合", result.get(1).getPortfolioName());
    }

    // 若有空置方法，补充如下：
    // @Test
    // public void testSomeBusinessMethod() {
    //     indexPortfolioService.someBusinessMethod(null);
    // }
} 