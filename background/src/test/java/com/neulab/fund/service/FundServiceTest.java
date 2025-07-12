package com.neulab.fund.service;

import com.neulab.fund.entity.Fund;
import com.neulab.fund.repository.FundRepository;
import com.neulab.fund.service.impl.FundServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FundServiceTest {
    @Mock
    private FundRepository fundRepository;
    @InjectMocks
    private FundServiceImpl fundService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllFunds() {
        // Given
        Fund fund1 = new Fund();
        fund1.setId(1L);
        fund1.setCode("FUND001");
        fund1.setName("基金1");

        Fund fund2 = new Fund();
        fund2.setId(2L);
        fund2.setCode("FUND002");
        fund2.setName("基金2");

        List<Fund> funds = Arrays.asList(fund1, fund2);
        when(fundRepository.findAll()).thenReturn(funds);

        // When
        List<Fund> result = fundService.getAllFunds();

        // Then
        assertEquals(2, result.size());
        assertEquals("基金1", result.get(0).getName());
        assertEquals("基金2", result.get(1).getName());
    }

    @Test
    void testGetFundById() {
        // Given
        Long fundId = 1L;
        Fund fund = new Fund();
        fund.setId(fundId);
        fund.setCode("FUND001");
        fund.setName("测试基金");
        when(fundRepository.findById(fundId)).thenReturn(Optional.of(fund));

        // When
        Fund result = fundService.getFundById(fundId);

        // Then
        assertNotNull(result);
        assertEquals("测试基金", result.getName());
        assertEquals("FUND001", result.getCode());
    }

    @Test
    void testGetFundByIdNotFound() {
        // Given
        Long fundId = 999L;
        when(fundRepository.findById(fundId)).thenReturn(Optional.empty());

        // When
        Fund result = fundService.getFundById(fundId);

        // Then
        assertNull(result);
    }

    @Test
    void testCreateFund() {
        // Given
        Fund fund = new Fund();
        fund.setCode("FUND001");
        fund.setName("新基金");
        fund.setType("股票型");
        fund.setStatus("active");

        Fund savedFund = new Fund();
        savedFund.setId(1L);
        savedFund.setCode("FUND001");
        savedFund.setName("新基金");
        savedFund.setType("股票型");
        savedFund.setStatus("active");

        when(fundRepository.save(any(Fund.class))).thenReturn(savedFund);

        // When
        Fund result = fundService.createFund(fund);

        // Then
        assertNotNull(result);
        assertEquals("新基金", result.getName());
        assertEquals("FUND001", result.getCode());
        verify(fundRepository).save(fund);
    }

    @Test
    void testUpdateFund() {
        // Given
        Long fundId = 1L;
        Fund existingFund = new Fund();
        existingFund.setId(fundId);
        existingFund.setName("原基金");

        Fund updatedFund = new Fund();
        updatedFund.setId(fundId);
        updatedFund.setName("更新基金");

        when(fundRepository.findById(fundId)).thenReturn(Optional.of(existingFund));
        when(fundRepository.save(any(Fund.class))).thenReturn(updatedFund);

        // When
        Fund result = fundService.saveFund(updatedFund);

        // Then
        assertNotNull(result);
        assertEquals("更新基金", result.getName());
        verify(fundRepository).save(updatedFund);
    }

    @Test
    void testDeleteFund() {
        // Given
        Long fundId = 1L;
        Fund fund = new Fund();
        fund.setId(fundId);
        fund.setName("要删除的基金");

        when(fundRepository.findById(fundId)).thenReturn(Optional.of(fund));
        doNothing().when(fundRepository).deleteById(fundId);

        // When
        fundService.deleteFund(fundId);

        // Then
        verify(fundRepository).deleteById(fundId);
    }
} 