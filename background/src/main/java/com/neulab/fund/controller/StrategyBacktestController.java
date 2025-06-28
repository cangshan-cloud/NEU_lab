package com.neulab.fund.controller;

import com.neulab.fund.entity.StrategyBacktest;
import com.neulab.fund.service.StrategyBacktestService;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 策略回测相关接口
 */
@RestController
@RequestMapping("/api/strategy-backtests")
public class StrategyBacktestController {
    private final StrategyBacktestService strategyBacktestService;

    public StrategyBacktestController(StrategyBacktestService strategyBacktestService) {
        this.strategyBacktestService = strategyBacktestService;
    }

    /**
     * 查询全部回测记录
     */
    @GetMapping
    public ApiResponse<List<StrategyBacktest>> getAllBacktests() {
        return ApiResponse.success(strategyBacktestService.getAllBacktests());
    }

    /**
     * 根据ID查询回测记录
     */
    @GetMapping("/{id}")
    public ApiResponse<StrategyBacktest> getBacktestById(@PathVariable Long id) {
        return ApiResponse.success(strategyBacktestService.getBacktestById(id));
    }

    /**
     * 新增回测记录
     */
    @PostMapping
    public ApiResponse<StrategyBacktest> createBacktest(@RequestBody StrategyBacktest backtest) {
        return ApiResponse.success(strategyBacktestService.createBacktest(backtest));
    }
} 