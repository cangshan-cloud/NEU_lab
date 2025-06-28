package com.neulab.fund.controller;

import com.neulab.fund.entity.FactorData;
import com.neulab.fund.service.FactorDataService;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 因子数据相关接口
 */
@RestController
@RequestMapping("/api/factor-data")
public class FactorDataController {
    private final FactorDataService factorDataService;

    public FactorDataController(FactorDataService factorDataService) {
        this.factorDataService = factorDataService;
    }

    /**
     * 查询全部因子数据
     */
    @GetMapping
    public ApiResponse<List<FactorData>> getAllData() {
        return ApiResponse.success(factorDataService.getAllData());
    }

    /**
     * 根据ID查询因子数据
     */
    @GetMapping("/{id}")
    public ApiResponse<FactorData> getDataById(@PathVariable Long id) {
        return ApiResponse.success(factorDataService.getDataById(id));
    }

    /**
     * 新增因子数据
     */
    @PostMapping
    public ApiResponse<FactorData> createData(@RequestBody FactorData data) {
        return ApiResponse.success(factorDataService.createData(data));
    }
} 