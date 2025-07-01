package com.neulab.fund.controller;

import com.neulab.fund.entity.FundPortfolio;
import com.neulab.fund.repository.FundPortfolioRepository;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 基金组合相关接口
 */
@RestController
@RequestMapping("/api/fund-portfolios")
public class FundPortfolioController {
    private final FundPortfolioRepository fundPortfolioRepository;

    public FundPortfolioController(FundPortfolioRepository fundPortfolioRepository) {
        this.fundPortfolioRepository = fundPortfolioRepository;
    }

    /**
     * 查询全部组合
     */
    @GetMapping
    public ApiResponse<List<FundPortfolio>> getAllPortfolios() {
        return ApiResponse.success(fundPortfolioRepository.findAll());
    }

    /**
     * 根据ID查询组合
     */
    @GetMapping("/{id}")
    public ApiResponse<FundPortfolio> getPortfolioById(@PathVariable Long id) {
        return ApiResponse.success(fundPortfolioRepository.findById(id).orElse(null));
    }

    /**
     * 新增组合
     */
    @PostMapping
    public ApiResponse<FundPortfolio> createPortfolio(@RequestBody FundPortfolio portfolio) {
        return ApiResponse.success(fundPortfolioRepository.save(portfolio));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePortfolio(@PathVariable Long id) {
        fundPortfolioRepository.deleteById(id);
        return ApiResponse.success(null);
    }
} 