package com.neulab.fund.controller;

import com.neulab.fund.entity.FundCompany;
import com.neulab.fund.service.FundCompanyService;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 基金公司相关接口
 */
@RestController
@RequestMapping("/api/fund-companies")
public class FundCompanyController {
    private final FundCompanyService fundCompanyService;

    public FundCompanyController(FundCompanyService fundCompanyService) {
        this.fundCompanyService = fundCompanyService;
    }

    /**
     * 查询全部公司
     */
    @GetMapping
    public ApiResponse<List<FundCompany>> getAllCompanies() {
        return ApiResponse.success(fundCompanyService.getAllCompanies());
    }

    /**
     * 根据ID查询公司
     */
    @GetMapping("/{id}")
    public ApiResponse<FundCompany> getCompanyById(@PathVariable Long id) {
        return ApiResponse.success(fundCompanyService.getCompanyById(id));
    }

    /**
     * 新增公司
     */
    @PostMapping
    public ApiResponse<FundCompany> createCompany(@RequestBody FundCompany company) {
        return ApiResponse.success(fundCompanyService.createCompany(company));
    }
} 