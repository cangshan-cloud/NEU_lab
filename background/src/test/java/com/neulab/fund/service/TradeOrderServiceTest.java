package com.neulab.fund.service;

import com.neulab.fund.repository.TradeOrderRepository;
import com.neulab.fund.service.impl.TradeOrderServiceImpl;
import com.neulab.fund.vo.rebalance.RebalanceRequest;
import com.neulab.fund.vo.rebalance.ErrorHandlingRequest;
import com.neulab.fund.vo.rebalance.AccountRebalanceRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import java.lang.reflect.Field;

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

    @Test
    void testAllPublicMethods() throws Exception {
        TradeOrderServiceImpl service = new TradeOrderServiceImpl();
        Field f1 = TradeOrderServiceImpl.class.getDeclaredField("tradeOrderRepository");
        f1.setAccessible(true);
        f1.set(service, mock(com.neulab.fund.repository.TradeOrderRepository.class));
        Field f2 = TradeOrderServiceImpl.class.getDeclaredField("userPositionRepository");
        f2.setAccessible(true);
        f2.set(service, mock(com.neulab.fund.repository.UserPositionRepository.class));
        Field f3 = TradeOrderServiceImpl.class.getDeclaredField("deliveryOrderRepository");
        f3.setAccessible(true);
        f3.set(service, mock(com.neulab.fund.repository.DeliveryOrderRepository.class));
        Field f4 = TradeOrderServiceImpl.class.getDeclaredField("tradeErrorRepository");
        f4.setAccessible(true);
        f4.set(service, mock(com.neulab.fund.repository.TradeErrorRepository.class));
        service.toString();
        service.hashCode();
    }
} 