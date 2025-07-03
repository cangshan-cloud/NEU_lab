package com.neulab.fund.service;

import com.neulab.fund.repository.FactorTreeRepository;
import com.neulab.fund.service.impl.FactorTreeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FactorTreeServiceTest {

    @Mock
    private FactorTreeRepository factorTreeRepository;

    @InjectMocks
    private FactorTreeServiceImpl factorTreeService;

    @Test
    void testToString() {
        assertDoesNotThrow(() -> factorTreeService.toString());
    }
} 