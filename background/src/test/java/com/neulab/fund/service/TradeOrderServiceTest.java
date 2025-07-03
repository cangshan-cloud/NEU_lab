package com.neulab.fund.service;

import com.neulab.fund.repository.TradeOrderRepository;
import com.neulab.fund.service.impl.TradeOrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TradeOrderServiceTest {

    @Mock
    private TradeOrderRepository tradeOrderRepository;

    @InjectMocks
    private TradeOrderServiceImpl tradeOrderService;

    @Test
    void testToString() {
        assertDoesNotThrow(() -> tradeOrderService.toString());
    }
} 