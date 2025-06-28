package com.neulab.fund.controller;

import com.neulab.fund.entity.IndexPortfolio;
import com.neulab.fund.service.IndexPortfolioService;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 基金指数组合相关接口
 */
@RestController
@RequestMapping("/api/index-portfolios")
public class IndexPortfolioController {
    private final IndexPortfolioService indexPortfolioService;

    public IndexPortfolioController(IndexPortfolioService indexPortfolioService) {
        this.indexPortfolioService = indexPortfolioService;
    }

    /**
     * 查询全部基金指数组合
     */
    @GetMapping
    public ApiResponse<List<IndexPortfolio>> getAllIndexPortfolios() {
        return ApiResponse.success(indexPortfolioService.getAllIndexPortfolios());
    }

    /**
     * 根据ID查询基金指数组合
     */
    @GetMapping("/{id}")
    public ApiResponse<IndexPortfolio> getIndexPortfolioById(@PathVariable Long id) {
        return ApiResponse.success(indexPortfolioService.getIndexPortfolioById(id));
    }

    /**
     * 新增基金指数组合
     */
    @PostMapping
    public ApiResponse<IndexPortfolio> createIndexPortfolio(@RequestBody IndexPortfolio indexPortfolio) {
        return ApiResponse.success(indexPortfolioService.createIndexPortfolio(indexPortfolio));
    }
} 