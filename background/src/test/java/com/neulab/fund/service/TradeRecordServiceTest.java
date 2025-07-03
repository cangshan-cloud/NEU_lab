package com.neulab.fund.service;

import com.neulab.fund.repository.TradeRecordRepository;
import com.neulab.fund.service.impl.TradeRecordServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TradeRecordServiceTest {

    @Mock
    private TradeRecordRepository tradeRecordRepository;

    @InjectMocks
    private TradeRecordServiceImpl tradeRecordService;

    @Test
    void testToString() {
        assertDoesNotThrow(() -> tradeRecordService.toString());
    }
} 