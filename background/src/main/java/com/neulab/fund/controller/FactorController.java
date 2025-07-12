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
        Factor factor = factorService.getFactorById(id);
        if (factor == null) {
            return ApiResponse.error("因子不存在", 1);
        }
        return ApiResponse.success(factor);
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
        Factor factor = factorService.getFactorById(id);
        if (factor == null) {
            return ApiResponse.error("因子不存在", 1);
        }
        factorService.deleteFactor(id);
        return new ApiResponse<>(0, "删除成功", null);
    }

    @PutMapping("/{id}")
    public ApiResponse<Factor> updateFactor(@PathVariable Long id, @RequestBody Factor factor) {
        Factor existing = factorService.getFactorById(id);
        if (existing == null) {
            return ApiResponse.error("因子不存在", 1);
        }
        factor.setId(id);
        Factor updated = factorService.updateFactor(factor);
        return ApiResponse.success(updated);
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