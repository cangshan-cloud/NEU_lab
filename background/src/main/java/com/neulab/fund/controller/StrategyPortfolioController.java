package com.neulab.fund.controller;

import com.neulab.fund.entity.StrategyPortfolio;
import com.neulab.fund.service.StrategyPortfolioService;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 策略与组合关联相关接口
 */
@RestController
@RequestMapping("/api/strategy-portfolios")
public class StrategyPortfolioController {
    private final StrategyPortfolioService strategyPortfolioService;

    public StrategyPortfolioController(StrategyPortfolioService strategyPortfolioService) {
        this.strategyPortfolioService = strategyPortfolioService;
    }

    /**
     * 查询全部关联
     */
    @GetMapping
    public ApiResponse<List<StrategyPortfolio>> getAllRelations() {
        return ApiResponse.success(strategyPortfolioService.getAllRelations());
    }

    /**
     * 根据ID查询关联
     */
    @GetMapping("/{id}")
    public ApiResponse<StrategyPortfolio> getRelationById(@PathVariable Long id) {
        return ApiResponse.success(strategyPortfolioService.getRelationById(id));
    }

    /**
     * 新增关联
     */
    @PostMapping
    public ApiResponse<StrategyPortfolio> createRelation(@RequestBody StrategyPortfolio relation) {
        return ApiResponse.success(strategyPortfolioService.createRelation(relation));
    }
} 