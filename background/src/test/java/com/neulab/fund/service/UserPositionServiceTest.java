package com.neulab.fund.service;

import com.neulab.fund.repository.UserPositionRepository;
import com.neulab.fund.service.impl.UserPositionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserPositionServiceTest {

    @Mock
    private UserPositionRepository userPositionRepository;

    @InjectMocks
    private UserPositionServiceImpl userPositionService;

    @Test
    void testToString() {
        assertDoesNotThrow(() -> userPositionService.toString());
    }
} 