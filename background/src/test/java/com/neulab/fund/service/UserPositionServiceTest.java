package com.neulab.fund.service;

import com.neulab.fund.repository.UserPositionRepository;
import com.neulab.fund.service.impl.UserPositionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neulab.fund.entity.UserPosition;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import static org.mockito.Mockito.*;

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

    @Test
    void testCreatePosition() {
        UserPosition pos = new UserPosition();
        when(userPositionRepository.save(any())).thenReturn(pos);
        assertNotNull(userPositionService.createPosition(pos));
    }
    @Test
    void testFindById() {
        UserPosition pos = new UserPosition();
        when(userPositionRepository.findById(1L)).thenReturn(Optional.of(pos));
        assertTrue(userPositionService.findById(1L).isPresent());
    }
    @Test
    void testFindByUserIdAndProductId() {
        UserPosition pos = new UserPosition();
        when(userPositionRepository.findByUserIdAndProductId(1L,2L)).thenReturn(Optional.of(pos));
        assertTrue(userPositionService.findByUserIdAndProductId(1L,2L).isPresent());
    }
    @Test
    void testFindAll() {
        when(userPositionRepository.findAll()).thenReturn(Collections.emptyList());
        assertNotNull(userPositionService.findAll());
    }
    @Test
    void testFindByUserId() {
        when(userPositionRepository.findByUserIdOrderByUpdatedAtDesc(1L)).thenReturn(Collections.emptyList());
        assertNotNull(userPositionService.findByUserId(1L));
    }
    @Test
    void testFindByProductId() {
        when(userPositionRepository.findByProductIdOrderBySharesDesc(2L)).thenReturn(Collections.emptyList());
        assertNotNull(userPositionService.findByProductId(2L));
    }
    @Test
    void testUpdatePosition() {
        UserPosition pos = new UserPosition();
        when(userPositionRepository.save(any())).thenReturn(pos);
        assertNotNull(userPositionService.updatePosition(pos));
    }
    @Test
    void testUpdateShares_Found() {
        UserPosition pos = new UserPosition();
        when(userPositionRepository.findByUserIdAndProductId(1L,2L)).thenReturn(Optional.of(pos));
        when(userPositionRepository.save(any())).thenReturn(pos);
        assertNotNull(userPositionService.updateShares(1L,2L,BigDecimal.ONE));
    }
    @Test
    void testUpdateShares_NotFound() {
        when(userPositionRepository.findByUserIdAndProductId(1L,2L)).thenReturn(Optional.empty());
        assertNull(userPositionService.updateShares(1L,2L,BigDecimal.ONE));
    }
    @Test
    void testUpdateMarketValue_Found() {
        UserPosition pos = new UserPosition();
        pos.setCost(BigDecimal.TEN);
        when(userPositionRepository.findByUserIdAndProductId(1L,2L)).thenReturn(Optional.of(pos));
        when(userPositionRepository.save(any())).thenReturn(pos);
        assertNotNull(userPositionService.updateMarketValue(1L,2L,BigDecimal.valueOf(20)));
    }
    @Test
    void testUpdateMarketValue_NotFound() {
        when(userPositionRepository.findByUserIdAndProductId(1L,2L)).thenReturn(Optional.empty());
        assertNull(userPositionService.updateMarketValue(1L,2L,BigDecimal.TEN));
    }
    @Test
    void testDeletePosition() {
        doNothing().when(userPositionRepository).deleteById(1L);
        assertDoesNotThrow(() -> userPositionService.deletePosition(1L));
    }
    @Test
    void testCountDistinctUsers() {
        when(userPositionRepository.countDistinctUsers()).thenReturn(1L);
        assertEquals(1L, userPositionService.countDistinctUsers());
    }
    @Test
    void testCountByProductId() {
        when(userPositionRepository.countByProductId(2L)).thenReturn(2L);
        assertEquals(2L, userPositionService.countByProductId(2L));
    }
    @Test
    void testSumMarketValueByUserId() {
        when(userPositionRepository.sumMarketValueByUserId(1L)).thenReturn(BigDecimal.TEN);
        assertEquals(BigDecimal.TEN, userPositionService.sumMarketValueByUserId(1L));
    }
    @Test
    void testSumCostByUserId() {
        when(userPositionRepository.sumCostByUserId(1L)).thenReturn(BigDecimal.ONE);
        assertEquals(BigDecimal.ONE, userPositionService.sumCostByUserId(1L));
    }
    @Test
    void testSumProfitLossByUserId() {
        when(userPositionRepository.sumProfitLossByUserId(1L)).thenReturn(BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, userPositionService.sumProfitLossByUserId(1L));
    }
} 