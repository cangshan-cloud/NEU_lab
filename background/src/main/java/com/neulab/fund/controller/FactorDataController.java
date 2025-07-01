package com.neulab.fund.controller;

import com.neulab.fund.entity.FactorData;
import com.neulab.fund.service.FactorDataService;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 因子数据相关接口
 */
@RestController
@RequestMapping("/api/factor-data")
public class FactorDataController {
    @Autowired
    private FactorDataService factorDataService;

    /**
     * 查询全部因子数据
     */
    @GetMapping
    public ApiResponse<List<FactorData>> getAllFactorData() {
        return ApiResponse.success(factorDataService.getAllFactorData());
    }

    /**
     * 根据ID查询因子数据
     */
    @GetMapping("/{id}")
    public ApiResponse<FactorData> getFactorData(@PathVariable Long id) {
        return ApiResponse.success(factorDataService.getFactorDataById(id));
    }

    /**
     * 新增因子数据
     */
    @PostMapping
    public ApiResponse<FactorData> addFactorData(@RequestBody FactorData factorData) {
        return ApiResponse.success(factorDataService.saveFactorData(factorData));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteFactorData(@PathVariable Long id) {
        factorDataService.deleteFactorData(id);
        return ApiResponse.success();
    }

    @PutMapping
    public ApiResponse<FactorData> updateFactorData(@RequestBody FactorData factorData) {
        return ApiResponse.success(factorDataService.updateFactorData(factorData));
    }
} 