package com.neulab.fund.service;

import com.neulab.fund.repository.FundManagerRepository;
import com.neulab.fund.service.impl.FundManagerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FundManagerServiceTest {

    @Mock
    private FundManagerRepository fundManagerRepository;

    @InjectMocks
    private FundManagerServiceImpl fundManagerService;

    @Test
    void testToString() {
        assertDoesNotThrow(() -> fundManagerService.toString());
    }
} 