package com.neulab.fund.controller;

import com.neulab.fund.entity.Fund;
import com.neulab.fund.service.FundService;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 基金相关接口
 */
@RestController
@RequestMapping("/api/funds")
public class FundController {
    private final FundService fundService;

    public FundController(FundService fundService) {
        this.fundService = fundService;
    }

    /**
     * 查询全部基金
     */
    @GetMapping
    public ApiResponse<List<Fund>> getAllFunds() {
        return ApiResponse.success(fundService.getAllFunds());
    }

    /**
     * 根据ID查询基金
     */
    @GetMapping("/{id}")
    public ApiResponse<Fund> getFundById(@PathVariable Long id) {
        return ApiResponse.success(fundService.getFundById(id));
    }

    /**
     * 新增基金
     */
    @PostMapping
    public ApiResponse<Fund> createFund(@RequestBody Fund fund) {
        return ApiResponse.success(fundService.createFund(fund));
    }
} 