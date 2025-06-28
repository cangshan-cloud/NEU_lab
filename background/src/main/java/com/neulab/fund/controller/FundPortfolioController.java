package com.neulab.fund.controller;

import com.neulab.fund.entity.FundPortfolio;
import com.neulab.fund.service.FundPortfolioService;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 基金组合相关接口
 */
@RestController
@RequestMapping("/api/fund-portfolios")
public class FundPortfolioController {
    private final FundPortfolioService fundPortfolioService;

    public FundPortfolioController(FundPortfolioService fundPortfolioService) {
        this.fundPortfolioService = fundPortfolioService;
    }

    /**
     * 查询全部组合
     */
    @GetMapping
    public ApiResponse<List<FundPortfolio>> getAllPortfolios() {
        return ApiResponse.success(fundPortfolioService.getAllPortfolios());
    }

    /**
     * 根据ID查询组合
     */
    @GetMapping("/{id}")
    public ApiResponse<FundPortfolio> getPortfolioById(@PathVariable Long id) {
        return ApiResponse.success(fundPortfolioService.getPortfolioById(id));
    }

    /**
     * 新增组合
     */
    @PostMapping
    public ApiResponse<FundPortfolio> createPortfolio(@RequestBody FundPortfolio portfolio) {
        return ApiResponse.success(fundPortfolioService.createPortfolio(portfolio));
    }
} 