package com.neulab.fund.controller;

import com.neulab.fund.entity.Factor;
import com.neulab.fund.service.FactorService;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 因子相关接口
 */
@RestController
@RequestMapping("/api/factors")
public class FactorController {
    @Autowired
    private FactorService factorService;

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
    public ApiResponse<Factor> getFactor(@PathVariable Long id) {
        return ApiResponse.success(factorService.getFactorById(id));
    }

    /**
     * 新增因子
     */
    @PostMapping
    public ApiResponse<Factor> addFactor(@RequestBody Factor factor) {
        return ApiResponse.success(factorService.saveFactor(factor));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteFactor(@PathVariable Long id) {
        factorService.deleteFactor(id);
        return ApiResponse.success();
    }

    @PutMapping
    public ApiResponse<Factor> updateFactor(@RequestBody Factor factor) {
        return ApiResponse.success(factorService.updateFactor(factor));
    }

    /**
     * 批量导入因子
     */
    @PostMapping("/batch")
    public ApiResponse<Void> batchImportFactors(@RequestBody List<Factor> factors) {
        factorService.batchImport(factors);
        return ApiResponse.success();
    }
} 