package com.neulab.fund.controller;

import com.neulab.fund.entity.Fund;
import com.neulab.fund.entity.FundTag;
import com.neulab.fund.service.FundService;
import com.neulab.fund.repository.FundTagRepository;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基金相关接口
 */
@RestController
@RequestMapping("/api/funds")
public class FundController {
    private final FundService fundService;
    private final FundTagRepository fundTagRepository;

    public FundController(FundService fundService, FundTagRepository fundTagRepository) {
        this.fundService = fundService;
        this.fundTagRepository = fundTagRepository;
    }

    /**
     * 查询全部基金
     */
    @GetMapping("/all")
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
     * 多条件、标签筛选基金（分页）
     */
    @GetMapping
    public ApiResponse<Page<Fund>> getFunds(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) Long managerId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String riskLevel,
            @RequestParam(value = "tagIds", required = false) List<Long> tagIds,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Map<String, Object> filters = new java.util.HashMap<>();
        if (keyword != null) filters.put("keyword", keyword);
        if (companyId != null) filters.put("companyId", companyId);
        if (managerId != null) filters.put("managerId", managerId);
        if (type != null) filters.put("type", type);
        if (riskLevel != null) filters.put("riskLevel", riskLevel);
        if (tagIds != null && !tagIds.isEmpty()) filters.put("tagIds", tagIds);
        Page<Fund> result = fundService.getFundsWithFilter(filters, pageable);
        return ApiResponse.success(result);
    }

    /**
     * 新增基金，支持标签
     */
    @PostMapping
    public ApiResponse<Fund> createFund(@RequestBody Fund fund, @RequestParam(required = false) ArrayList<Long> tagIds) {
        if (tagIds != null && !tagIds.isEmpty()) {
            fund.setTags(fundTagRepository.findAllById(tagIds));
        }
        return ApiResponse.success(fundService.createFund(fund));
    }

    /**
     * 编辑基金，支持标签
     */
    @PutMapping("/{id}")
    public ApiResponse<Fund> updateFund(@PathVariable Long id, @RequestBody Fund fund, @RequestParam(required = false) ArrayList<Long> tagIds) {
        fund.setId(id);
        if (tagIds != null && !tagIds.isEmpty()) {
            fund.setTags(fundTagRepository.findAllById(tagIds));
        }
        return ApiResponse.success(fundService.saveFund(fund));
    }

    /**
     * 删除基金
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteFund(@PathVariable Long id) {
        fundService.deleteFund(id);
        return ApiResponse.success(null);
    }

    /**
     * 基金画像接口
     */
    @GetMapping("/{id}/profile")
    public ApiResponse<Fund> getFundProfile(@PathVariable Long id) {
        Fund fund = fundService.getFundById(id);
        // 可扩展聚合业绩、持仓、公告等
        return ApiResponse.success(fund);
    }

    /**
     * 查询全部基金（VO版）
     */
    @GetMapping("/all-vo")
    public ApiResponse<List<com.neulab.fund.vo.FundListVO>> getAllFundVOs() {
        return ApiResponse.success(fundService.getAllFundVOs());
    }

    /**
     * 多条件、标签筛选基金（分页，VO版）
     */
    @GetMapping("/vo")
    public ApiResponse<org.springframework.data.domain.Page<com.neulab.fund.vo.FundListVO>> getFundVOs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) Long managerId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String riskLevel,
            @RequestParam(value = "tagIds", required = false) List<Long> tagIds,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        java.util.Map<String, Object> filters = new java.util.HashMap<>();
        if (keyword != null) filters.put("keyword", keyword);
        if (companyId != null) filters.put("companyId", companyId);
        if (managerId != null) filters.put("managerId", managerId);
        if (type != null) filters.put("type", type);
        if (riskLevel != null) filters.put("riskLevel", riskLevel);
        if (tagIds != null && !tagIds.isEmpty()) filters.put("tagIds", tagIds);
        org.springframework.data.domain.Page<com.neulab.fund.vo.FundListVO> result = fundService.getFundVOsWithFilter(filters, pageable);
        return ApiResponse.success(result);
    }
} 