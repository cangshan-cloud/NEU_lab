package com.neulab.fund.service;

import com.neulab.fund.repository.FundCompanyRepository;
import com.neulab.fund.service.impl.FundCompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FundCompanyServiceTest {

    @Mock
    private FundCompanyRepository fundCompanyRepository;

    @InjectMocks
    private FundCompanyServiceImpl fundCompanyService;

    @Test
    void testToString() {
        assertDoesNotThrow(() -> fundCompanyService.toString());
    }
} 