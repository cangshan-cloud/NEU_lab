package com.neulab.fund.controller;

import com.neulab.fund.entity.StrategyBacktest;
import com.neulab.fund.service.StrategyBacktestService;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * 策略回测相关接口
 */
@RestController
@RequestMapping("/api/strategy-backtests")
public class StrategyBacktestController {
    @Autowired
    private StrategyBacktestService strategyBacktestService;

    public StrategyBacktestController() {
        System.out.println("StrategyBacktestController loaded!");
    }

    /**
     * 分页+按策略ID查询回测历史
     */
    @GetMapping
    public ApiResponse<Page<StrategyBacktest>> getBacktests(
            @RequestParam(required = false) Long strategyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        System.out.println("getBacktests called! strategyId=" + strategyId + ", page=" + page + ", size=" + size);
        Pageable pageable = PageRequest.of(page, size);
        Page<StrategyBacktest> result;
        if (strategyId != null) {
            result = strategyBacktestService.findByStrategyId(strategyId, pageable);
        } else {
            result = strategyBacktestService.findAll(pageable);
        }
        return ApiResponse.success(result);
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