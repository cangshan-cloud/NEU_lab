package com.neulab.fund.service;

import com.neulab.fund.entity.Fund;
import com.neulab.fund.repository.FundRepository;
import com.neulab.fund.service.impl.FundServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

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
        List<Fund> list = Arrays.asList(new Fund(), new Fund());
        when(fundRepository.findAll()).thenReturn(list);
        List<Fund> result = fundService.getAllFunds();
        assertEquals(2, result.size());
    }

    @Test
    void testGetFundByIdFound() {
        Fund fund = new Fund();
        fund.setId(1L);
        when(fundRepository.findById(1L)).thenReturn(Optional.of(fund));
        Fund result = fundService.getFundById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetFundByIdNotFound() {
        when(fundRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> fundService.getFundById(2L));
    }

    @Test
    void testGetFundsWithFilter() {
        Pageable pageable = PageRequest.of(0, 2);
        List<Fund> list = Arrays.asList(new Fund(), new Fund());
        Page<Fund> page = new PageImpl<>(list, pageable, 2);
        when(fundRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        Map<String, Object> filters = new HashMap<>();
        Page<Fund> result = fundService.getFundsWithFilter(filters, pageable);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void testCreateFund() {
        Fund fund = new Fund();
        fund.setName("新基金");
        when(fundRepository.save(fund)).thenReturn(fund);
        Fund result = fundService.createFund(fund);
        assertEquals("新基金", result.getName());
    }

    @Test
    void testSaveFund() {
        Fund fund = new Fund();
        fund.setName("保存基金");
        when(fundRepository.save(fund)).thenReturn(fund);
        Fund result = fundService.saveFund(fund);
        assertEquals("保存基金", result.getName());
    }

    @Test
    void testDeleteFund() {
        doNothing().when(fundRepository).deleteById(1L);
        assertDoesNotThrow(() -> fundService.deleteFund(1L));
        verify(fundRepository, times(1)).deleteById(1L);
    }
} 