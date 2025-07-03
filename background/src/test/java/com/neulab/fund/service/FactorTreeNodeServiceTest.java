package com.neulab.fund.service;

import com.neulab.fund.repository.FactorTreeNodeRepository;
import com.neulab.fund.service.impl.FactorTreeNodeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FactorTreeNodeServiceTest {

    @Mock
    private FactorTreeNodeRepository factorTreeNodeRepository;

    @InjectMocks
    private FactorTreeNodeServiceImpl factorTreeNodeService;

    @Test
    void testToString() {
        assertDoesNotThrow(() -> factorTreeNodeService.toString());
    }
} 