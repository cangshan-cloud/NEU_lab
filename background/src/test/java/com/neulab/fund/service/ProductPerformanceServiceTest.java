package com.neulab.fund.service;

import com.neulab.fund.entity.ProductPerformance;
import com.neulab.fund.repository.ProductPerformanceRepository;
import com.neulab.fund.service.impl.ProductPerformanceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductPerformanceServiceTest {

    @Mock
    private ProductPerformanceRepository productPerformanceRepository;

    @InjectMocks
    private ProductPerformanceServiceImpl productPerformanceService;

    private ProductPerformance testPerformance;

    @BeforeEach
    void setUp() {
        testPerformance = new ProductPerformance();
        testPerformance.setId(1L);
        testPerformance.setProductId(1L);
        // testPerformance.setDate(LocalDate.now());
    }

    @Test
    void testGetAllPerformances() {
        when(productPerformanceRepository.findAll()).thenReturn(Arrays.asList(testPerformance));
        
        List<ProductPerformance> result = productPerformanceService.getAllPerformances();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productPerformanceRepository).findAll();
    }

    @Test
    void testGetPerformanceById() {
        when(productPerformanceRepository.findById(1L)).thenReturn(Optional.of(testPerformance));
        
        ProductPerformance result = productPerformanceService.getPerformanceById(1L);
        
        assertNotNull(result);
        assertEquals(testPerformance, result);
        verify(productPerformanceRepository).findById(1L);
    }

    @Test
    void testCreatePerformance() {
        when(productPerformanceRepository.save(any(ProductPerformance.class))).thenReturn(testPerformance);
        
        ProductPerformance result = productPerformanceService.createPerformance(testPerformance);
        
        assertNotNull(result);
        assertEquals(testPerformance, result);
        verify(productPerformanceRepository).save(testPerformance);
    }

    @Test
    void testAllPublicMethods() {
        assertDoesNotThrow(() -> productPerformanceService.getAllPerformances());
        assertDoesNotThrow(() -> productPerformanceService.getPerformanceById(null));
        assertDoesNotThrow(() -> productPerformanceService.createPerformance(null));
    }
} 