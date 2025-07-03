package com.neulab.fund.service;

import com.neulab.fund.entity.Factor;
import com.neulab.fund.repository.FactorRepository;
import com.neulab.fund.service.impl.FactorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FactorServiceTest {

    @Mock
    private FactorRepository factorRepository;

    @InjectMocks
    private FactorServiceImpl factorService;

    private Factor testFactor;

    @BeforeEach
    void setUp() {
        testFactor = new Factor();
        testFactor.setId(1L);
        testFactor.setFactorCode("TEST001");
        testFactor.setFactorName("测试因子");
        testFactor.setDescription("测试因子描述");
        testFactor.setFactorType("STYLE");
    }

    @Test
    void testGetAllFactors() {
        when(factorRepository.findAll()).thenReturn(Arrays.asList(testFactor));
        List<Factor> result = factorService.getAllFactors();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(factorRepository).findAll();
    }

    @Test
    void testGetFactorById() {
        when(factorRepository.findById(1L)).thenReturn(java.util.Optional.of(testFactor));
        Factor result = factorService.getFactorById(1L);
        assertNotNull(result);
        assertEquals(testFactor, result);
        verify(factorRepository).findById(1L);
    }

    @Test
    void testCreateFactor() {
        when(factorRepository.save(any(Factor.class))).thenReturn(testFactor);
        Factor result = factorService.createFactor(testFactor);
        assertNotNull(result);
        assertEquals(testFactor, result);
        verify(factorRepository).save(testFactor);
    }
} 