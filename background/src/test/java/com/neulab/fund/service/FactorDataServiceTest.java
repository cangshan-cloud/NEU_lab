package com.neulab.fund.service;

import com.neulab.fund.repository.FactorDataRepository;
import com.neulab.fund.service.impl.FactorDataServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FactorDataServiceTest {

    @Mock
    private FactorDataRepository factorDataRepository;

    @InjectMocks
    private FactorDataServiceImpl factorDataService;

    @Test
    void testToString() {
        assertDoesNotThrow(() -> factorDataService.toString());
    }
} 