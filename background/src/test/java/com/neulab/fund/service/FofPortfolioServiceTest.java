package com.neulab.fund.service;

import com.neulab.fund.repository.FofPortfolioRepository;
import com.neulab.fund.service.impl.FofPortfolioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FofPortfolioServiceTest {

    @Mock
    private FofPortfolioRepository fofPortfolioRepository;

    @InjectMocks
    private FofPortfolioServiceImpl fofPortfolioService;

    @Test
    void testToString() {
        assertDoesNotThrow(() -> fofPortfolioService.toString());
    }
} 