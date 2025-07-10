package com.neulab.fund.controller;

import com.neulab.fund.service.StrategyService;
import com.neulab.fund.service.StrategyBacktestService;
import com.neulab.fund.vo.ApiResponse;
import com.neulab.fund.vo.StrategyDTO;
import com.neulab.fund.vo.StrategyVO;
import com.neulab.fund.vo.StrategyBacktestDTO;
import com.neulab.fund.vo.StrategyBacktestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

/**
 * 策略管理接口
 */
@RestController
@RequestMapping("/api/strategies")
public class StrategyController {
    @Autowired
    private StrategyService strategyService;
    @Autowired
    private StrategyBacktestService strategyBacktestService;

    /**
     * 分页+筛选查询策略列表
     */
    @GetMapping
    public ApiResponse<Page<StrategyVO>> listStrategies(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String riskLevel,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResponse.success(strategyService.pageList(keyword, type, riskLevel, status, pageable));
    }

    /**
     * 查询策略详情
     */
    @GetMapping("/{id}")
    public ApiResponse<StrategyVO> getStrategy(@PathVariable Long id) {
        return ApiResponse.success(strategyService.getStrategyVOById(id));
    }

    /**
     * 新建策略
     */
    @PostMapping
    public ApiResponse<StrategyVO> createStrategy(@Valid @RequestBody StrategyDTO dto) {
        return ApiResponse.success(strategyService.createStrategy(dto));
    }

    /**
     * 编辑策略
     */
    @PutMapping("/{id}")
    public ApiResponse<StrategyVO> updateStrategy(@PathVariable Long id, @Valid @RequestBody StrategyDTO dto) {
        return ApiResponse.success(strategyService.updateStrategy(id, dto));
    }

    /**
     * 删除策略
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteStrategy(@PathVariable Long id) {
        strategyService.deleteStrategy(id);
        return ApiResponse.success();
    }

    /**
     * 策略回测
     */
    @PostMapping("/{id}/backtest")
    public ApiResponse<StrategyBacktestVO> backtest(@PathVariable Long id, @Valid @RequestBody StrategyBacktestDTO dto) {
        return ApiResponse.success(strategyBacktestService.backtest(id, dto));
    }
} 