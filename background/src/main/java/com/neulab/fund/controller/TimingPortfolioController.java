package com.neulab.fund.controller;

import com.neulab.fund.entity.TimingPortfolio;
import com.neulab.fund.service.TimingPortfolioService;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 择时组合相关接口
 */
@RestController
@RequestMapping("/api/timing-portfolios")
public class TimingPortfolioController {
    private final TimingPortfolioService timingPortfolioService;

    public TimingPortfolioController(TimingPortfolioService timingPortfolioService) {
        this.timingPortfolioService = timingPortfolioService;
    }

    /**
     * 查询全部择时组合
     */
    @GetMapping
    public ApiResponse<List<TimingPortfolio>> getAllTimingPortfolios() {
        return ApiResponse.success(timingPortfolioService.getAllTimingPortfolios());
    }

    /**
     * 根据ID查询择时组合
     */
    @GetMapping("/{id}")
    public ApiResponse<TimingPortfolio> getTimingPortfolioById(@PathVariable Long id) {
        return ApiResponse.success(timingPortfolioService.getTimingPortfolioById(id));
    }

    /**
     * 新增择时组合
     */
    @PostMapping
    public ApiResponse<TimingPortfolio> createTimingPortfolio(@RequestBody TimingPortfolio timingPortfolio) {
        return ApiResponse.success(timingPortfolioService.createTimingPortfolio(timingPortfolio));
    }
} 