package com.neulab.fund.controller;

import com.neulab.fund.entity.FundManager;
import com.neulab.fund.service.FundManagerService;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 基金经理相关接口
 */
@RestController
@RequestMapping("/api/fund-managers")
public class FundManagerController {
    private final FundManagerService fundManagerService;

    public FundManagerController(FundManagerService fundManagerService) {
        this.fundManagerService = fundManagerService;
    }

    /**
     * 查询全部经理
     */
    @GetMapping
    public ApiResponse<List<FundManager>> getAllManagers() {
        return ApiResponse.success(fundManagerService.getAllManagers());
    }

    /**
     * 根据ID查询经理
     */
    @GetMapping("/{id}")
    public ApiResponse<FundManager> getManagerById(@PathVariable Long id) {
        return ApiResponse.success(fundManagerService.getManagerById(id));
    }

    /**
     * 新增经理
     */
    @PostMapping
    public ApiResponse<FundManager> createManager(@RequestBody FundManager manager) {
        return ApiResponse.success(fundManagerService.createManager(manager));
    }

    /**
     * 查询全部经理（VO版）
     */
    @GetMapping("/vo")
    public com.neulab.fund.vo.ApiResponse<java.util.List<com.neulab.fund.vo.FundManagerVO>> getAllManagerVOs() {
        return com.neulab.fund.vo.ApiResponse.success(fundManagerService.getAllManagerVOs());
    }
} 