package com.neulab.fund.service;

import com.neulab.fund.entity.Strategy;
import com.neulab.fund.repository.StrategyRepository;
import com.neulab.fund.service.impl.StrategyServiceImpl;
import com.neulab.fund.vo.StrategyDTO;
import com.neulab.fund.vo.StrategyVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StrategyServiceTest {
    @Mock
    private StrategyRepository strategyRepository;
    @InjectMocks
    private StrategyServiceImpl strategyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateStrategy() {
        // Given
        Strategy strategy = new Strategy();
        strategy.setStrategyCode("TEST001");
        strategy.setStrategyName("测试策略");
        strategy.setStrategyType("MOMENTUM");
        strategy.setStatus("ACTIVE");
        strategy.setDescription("这是一个测试策略");

        Strategy savedStrategy = new Strategy();
        savedStrategy.setId(1L);
        savedStrategy.setStrategyCode("TEST001");
        savedStrategy.setStrategyName("测试策略");
        savedStrategy.setStrategyType("MOMENTUM");
        savedStrategy.setStatus("ACTIVE");
        savedStrategy.setDescription("这是一个测试策略");

        when(strategyRepository.save(any(Strategy.class))).thenReturn(savedStrategy);

        // When
        Strategy result = strategyService.createStrategy(strategy);

        // Then
        assertNotNull(result);
        assertEquals("测试策略", result.getStrategyName());
        assertEquals("MOMENTUM", result.getStrategyType());
        verify(strategyRepository).save(any(Strategy.class));
    }

    @Test
    void testGetStrategyById() {
        // Given
        Long strategyId = 1L;
        Strategy strategy = new Strategy();
        strategy.setId(strategyId);
        strategy.setStrategyName("测试策略");
        strategy.setStrategyType("MOMENTUM");

        when(strategyRepository.findById(strategyId)).thenReturn(Optional.of(strategy));

        // When
        Strategy result = strategyService.getStrategyById(strategyId);

        // Then
        assertNotNull(result);
        assertEquals(strategyId, result.getId());
        assertEquals("测试策略", result.getStrategyName());
    }

    @Test
    void testGetStrategyByIdNotFound() {
        // Given
        Long strategyId = 999L;
        when(strategyRepository.findById(strategyId)).thenReturn(Optional.empty());

        // When
        Strategy result = strategyService.getStrategyById(strategyId);

        // Then
        assertNull(result);
    }

    @Test
    void testGetAllStrategies() {
        // Given
        Strategy strategy1 = new Strategy();
        strategy1.setId(1L);
        strategy1.setStrategyName("策略1");
        strategy1.setStrategyType("MOMENTUM");

        Strategy strategy2 = new Strategy();
        strategy2.setId(2L);
        strategy2.setStrategyName("策略2");
        strategy2.setStrategyType("MEAN_REVERSION");

        List<Strategy> strategies = Arrays.asList(strategy1, strategy2);
        when(strategyRepository.findAll()).thenReturn(strategies);

        // When
        List<Strategy> result = strategyService.getAllStrategies();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("策略1", result.get(0).getStrategyName());
        assertEquals("策略2", result.get(1).getStrategyName());
    }

    @Test
    void testUpdateStrategy() {
        // Given
        Long strategyId = 1L;
        Strategy strategy = new Strategy();
        strategy.setId(strategyId);
        strategy.setStrategyName("更新后的策略");
        strategy.setDescription("更新后的描述");
        strategy.setStrategyType("MEAN_REVERSION");

        when(strategyRepository.save(any(Strategy.class))).thenReturn(strategy);

        // When
        Strategy result = strategyService.updateStrategy(strategy);

        // Then
        assertNotNull(result);
        assertEquals("更新后的策略", result.getStrategyName());
        assertEquals("MEAN_REVERSION", result.getStrategyType());
        verify(strategyRepository).save(any(Strategy.class));
    }

    @Test
    void testDeleteStrategy() {
        // Given
        Long strategyId = 1L;
        doNothing().when(strategyRepository).deleteById(strategyId);

        // When
        strategyService.deleteStrategy(strategyId);

        // Then
        verify(strategyRepository).deleteById(strategyId);
    }

    @Test
    void testCreateStrategyWithDTO() {
        // Given
        StrategyDTO dto = new StrategyDTO();
        dto.setStrategyName("测试策略");
        dto.setStrategyType("MOMENTUM");
        dto.setDescription("这是一个测试策略");
        dto.setStatus("ACTIVE");

        Strategy strategy = new Strategy();
        strategy.setId(1L);
        strategy.setStrategyName("测试策略");
        strategy.setStrategyType("MOMENTUM");
        strategy.setDescription("这是一个测试策略");
        strategy.setStatus("ACTIVE");

        when(strategyRepository.save(any(Strategy.class))).thenReturn(strategy);

        // When
        StrategyVO result = strategyService.createStrategy(dto);

        // Then
        assertNotNull(result);
        assertEquals("测试策略", result.getStrategyName());
        assertEquals("MOMENTUM", result.getStrategyType());
    }

    @Test
    void testUpdateStrategyWithDTO() {
        // Given
        Long strategyId = 1L;
        StrategyDTO dto = new StrategyDTO();
        dto.setStrategyName("更新后的策略");
        dto.setStrategyType("MEAN_REVERSION");
        dto.setDescription("更新后的描述");

        Strategy existingStrategy = new Strategy();
        existingStrategy.setId(strategyId);
        existingStrategy.setStrategyName("原策略");
        existingStrategy.setStrategyType("MOMENTUM");

        Strategy updatedStrategy = new Strategy();
        updatedStrategy.setId(strategyId);
        updatedStrategy.setStrategyName("更新后的策略");
        updatedStrategy.setStrategyType("MEAN_REVERSION");
        updatedStrategy.setDescription("更新后的描述");

        when(strategyRepository.findById(strategyId)).thenReturn(Optional.of(existingStrategy));
        when(strategyRepository.save(any(Strategy.class))).thenReturn(updatedStrategy);

        // When
        StrategyVO result = strategyService.updateStrategy(strategyId, dto);

        // Then
        assertNotNull(result);
        assertEquals("更新后的策略", result.getStrategyName());
        assertEquals("MEAN_REVERSION", result.getStrategyType());
        verify(strategyRepository).save(any(Strategy.class));
    }

    @Test
    void testGetStrategyVOById() {
        // Given
        Long strategyId = 1L;
        Strategy strategy = new Strategy();
        strategy.setId(strategyId);
        strategy.setStrategyName("测试策略");
        strategy.setStrategyType("MOMENTUM");
        strategy.setDescription("测试描述");
        strategy.setStatus("ACTIVE");
        strategy.setCreatedAt(LocalDateTime.now());

        when(strategyRepository.findById(strategyId)).thenReturn(Optional.of(strategy));

        // When
        StrategyVO result = strategyService.getStrategyVOById(strategyId);

        // Then
        assertNotNull(result);
        assertEquals(strategyId, result.getId());
        assertEquals("测试策略", result.getStrategyName());
        assertEquals("MOMENTUM", result.getStrategyType());
    }

    @Test
    void testCreateAssetAllocationStrategy() {
        // Given
        Map<String, Object> config = new HashMap<>();
        config.put("name", "资产配置策略");
        config.put("description", "这是一个资产配置策略");
        config.put("riskLevel", "MEDIUM");
        config.put("targetReturn", 0.12);

        Strategy strategy = new Strategy();
        strategy.setId(1L);
        strategy.setStrategyName("资产配置策略");
        strategy.setStrategyType("ASSET_ALLOCATION");
        strategy.setDescription("这是一个资产配置策略");
        strategy.setStatus("ACTIVE");

        when(strategyRepository.save(any(Strategy.class))).thenReturn(strategy);

        // When
        Strategy result = strategyService.createAssetAllocationStrategy(config);

        // Then
        assertNotNull(result);
        assertEquals("资产配置策略", result.getStrategyName());
        assertEquals("ASSET_ALLOCATION", result.getStrategyType());
        assertEquals("ACTIVE", result.getStatus());
    }
} 