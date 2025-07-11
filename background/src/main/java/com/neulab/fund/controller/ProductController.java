package com.neulab.fund.controller;

import com.neulab.fund.entity.Product;
import com.neulab.fund.service.ProductService;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * 产品相关接口
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 查询全部产品
     */
    @GetMapping
    public ApiResponse<List<Product>> getAllProducts() {
        return ApiResponse.success(productService.getAllProducts());
    }

    /**
     * 根据ID查询产品
     */
    @GetMapping("/{id}")
    public ApiResponse<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ApiResponse.error("产品不存在", 1);
        }
        return ApiResponse.success(product);
    }

    /**
     * 新增产品
     */
    @PostMapping
    public ApiResponse<Product> createProduct(@RequestBody Product product) {
        return ApiResponse.success(productService.createProduct(product));
    }

    /**
     * 更新产品
     */
    @PutMapping("/{id}")
    public ApiResponse<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product existing = productService.getProductById(id);
        if (existing == null) {
            return ApiResponse.error("产品不存在", 1);
        }
        product.setId(id);
        return ApiResponse.success(productService.updateProduct(product));
    }

    /**
     * 删除产品
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ApiResponse.error("产品不存在", 1);
        }
        productService.deleteProduct(id);
        return new ApiResponse<>(0, "删除成功", null);
    }

    /**
     * 根据类型获取产品
     */
    @GetMapping("/type/{type}")
    public ApiResponse<List<Product>> getProductsByType(@PathVariable String type) {
        return ApiResponse.success(productService.getProductsByType(type));
    }

    /**
     * 根据风险等级获取产品
     */
    @GetMapping("/risk/{riskLevel}")
    public ApiResponse<List<Product>> getProductsByRiskLevel(@PathVariable String riskLevel) {
        return ApiResponse.success(productService.getProductsByRiskLevel(riskLevel));
    }

    /**
     * 根据策略ID获取产品
     */
    @GetMapping("/strategy/{strategyId}")
    public ApiResponse<List<Product>> getProductsByStrategyId(@PathVariable Long strategyId) {
        return ApiResponse.success(productService.getProductsByStrategyId(strategyId));
    }

    /**
     * 根据组合ID获取产品
     */
    @GetMapping("/portfolio/{portfolioId}")
    public ApiResponse<List<Product>> getProductsByPortfolioId(@PathVariable Long portfolioId) {
        return ApiResponse.success(productService.getProductsByPortfolioId(portfolioId));
    }

    /**
     * 查询全部产品（VO版）
     */
    @GetMapping("/vo")
    public com.neulab.fund.vo.ApiResponse<java.util.List<com.neulab.fund.vo.ProductVO>> getAllProductVOs() {
        return com.neulab.fund.vo.ApiResponse.success(productService.getAllProductVOs());
    }

    /**
     * 普通用户提交产品审核申请
     */
    @PostMapping("/{id}/submit-review")
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<Void> submitProductReview(@PathVariable Long id) {
        productService.submitProductReview(id);
        return ApiResponse.success(null);
    }
} 