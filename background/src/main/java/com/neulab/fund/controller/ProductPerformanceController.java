package com.neulab.fund.controller;

import com.neulab.fund.entity.ProductPerformance;
import com.neulab.fund.service.ProductPerformanceService;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 产品收益相关接口
 */
@RestController
@RequestMapping("/api/product-performances")
public class ProductPerformanceController {
    private final ProductPerformanceService productPerformanceService;

    public ProductPerformanceController(ProductPerformanceService productPerformanceService) {
        this.productPerformanceService = productPerformanceService;
    }

    /**
     * 查询全部收益记录
     */
    @GetMapping
    public ApiResponse<List<ProductPerformance>> getAllPerformances() {
        return ApiResponse.success(productPerformanceService.getAllPerformances());
    }

    /**
     * 根据ID查询收益记录
     */
    @GetMapping("/{id}")
    public ApiResponse<ProductPerformance> getPerformanceById(@PathVariable Long id) {
        return ApiResponse.success(productPerformanceService.getPerformanceById(id));
    }

    /**
     * 新增收益记录
     */
    @PostMapping
    public ApiResponse<ProductPerformance> createPerformance(@RequestBody ProductPerformance performance) {
        return ApiResponse.success(productPerformanceService.createPerformance(performance));
    }
} 