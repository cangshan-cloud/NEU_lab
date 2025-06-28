package com.neulab.fund.controller;

import com.neulab.fund.entity.Factor;
import com.neulab.fund.service.FactorService;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 因子相关接口
 */
@RestController
@RequestMapping("/api/factors")
public class FactorController {
    private final FactorService factorService;

    public FactorController(FactorService factorService) {
        this.factorService = factorService;
    }

    /**
     * 查询全部因子
     */
    @GetMapping
    public ApiResponse<List<Factor>> getAllFactors() {
        return ApiResponse.success(factorService.getAllFactors());
    }

    /**
     * 根据ID查询因子
     */
    @GetMapping("/{id}")
    public ApiResponse<Factor> getFactorById(@PathVariable Long id) {
        return ApiResponse.success(factorService.getFactorById(id));
    }

    /**
     * 新增因子
     */
    @PostMapping
    public ApiResponse<Factor> createFactor(@RequestBody Factor factor) {
        return ApiResponse.success(factorService.createFactor(factor));
    }
} 