package com.neulab.fund.service;

import com.neulab.fund.entity.CapitalFlow;
import com.neulab.fund.repository.CapitalFlowRepository;
import com.neulab.fund.service.impl.CapitalFlowServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CapitalFlowServiceTest {

    @Mock
    private CapitalFlowRepository capitalFlowRepository;

    @InjectMocks
    private CapitalFlowServiceImpl capitalFlowService;

    private CapitalFlow testFlow;

    @BeforeEach
    void setUp() {
        testFlow = new CapitalFlow();
        testFlow.setId(1L);
        testFlow.setUserId(1L);
        testFlow.setAmount(new BigDecimal("1000.00"));
        testFlow.setFlowType("IN");
        testFlow.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void testFindAll() {
        when(capitalFlowRepository.findAll()).thenReturn(Arrays.asList(testFlow));
        List<CapitalFlow> result = capitalFlowService.findAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(capitalFlowRepository).findAll();
    }

    @Test
    void testFindById() {
        when(capitalFlowRepository.findById(1L)).thenReturn(java.util.Optional.of(testFlow));
        java.util.Optional<CapitalFlow> result = capitalFlowService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(testFlow, result.get());
        verify(capitalFlowRepository).findById(1L);
    }

    @Test
    void testCreateFlow() {
        when(capitalFlowRepository.save(any(CapitalFlow.class))).thenReturn(testFlow);
        CapitalFlow result = capitalFlowService.createFlow(testFlow);
        assertNotNull(result);
        assertEquals(testFlow, result);
        verify(capitalFlowRepository).save(testFlow);
    }
} 