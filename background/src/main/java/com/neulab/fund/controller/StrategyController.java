package com.neulab.fund.controller;

import com.neulab.fund.entity.Strategy;
import com.neulab.fund.service.StrategyService;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 策略相关接口
 */
@RestController
@RequestMapping("/api/strategies")
public class StrategyController {
    private final StrategyService strategyService;

    public StrategyController(StrategyService strategyService) {
        this.strategyService = strategyService;
    }

    /**
     * 查询全部策略
     */
    @GetMapping
    public ApiResponse<List<Strategy>> getAllStrategies() {
        return ApiResponse.success(strategyService.getAllStrategies());
    }

    /**
     * 根据ID查询策略
     */
    @GetMapping("/{id}")
    public ApiResponse<Strategy> getStrategyById(@PathVariable Long id) {
        return ApiResponse.success(strategyService.getStrategyById(id));
    }

    /**
     * 新增策略
     */
    @PostMapping
    public ApiResponse<Strategy> createStrategy(@RequestBody Strategy strategy) {
        return ApiResponse.success(strategyService.createStrategy(strategy));
    }
} 