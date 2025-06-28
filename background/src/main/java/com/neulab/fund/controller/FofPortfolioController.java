package com.neulab.fund.controller;

import com.neulab.fund.entity.FofPortfolio;
import com.neulab.fund.service.FofPortfolioService;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FOF组合相关接口
 */
@RestController
@RequestMapping("/api/fof-portfolios")
public class FofPortfolioController {
    private final FofPortfolioService fofPortfolioService;

    public FofPortfolioController(FofPortfolioService fofPortfolioService) {
        this.fofPortfolioService = fofPortfolioService;
    }

    /**
     * 查询全部FOF组合
     */
    @GetMapping
    public ApiResponse<List<FofPortfolio>> getAllFofPortfolios() {
        return ApiResponse.success(fofPortfolioService.getAllFofPortfolios());
    }

    /**
     * 根据ID查询FOF组合
     */
    @GetMapping("/{id}")
    public ApiResponse<FofPortfolio> getFofPortfolioById(@PathVariable Long id) {
        return ApiResponse.success(fofPortfolioService.getFofPortfolioById(id));
    }

    /**
     * 新增FOF组合
     */
    @PostMapping
    public ApiResponse<FofPortfolio> createFofPortfolio(@RequestBody FofPortfolio fofPortfolio) {
        return ApiResponse.success(fofPortfolioService.createFofPortfolio(fofPortfolio));
    }
} 