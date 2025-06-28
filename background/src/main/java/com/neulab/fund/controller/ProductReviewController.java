package com.neulab.fund.controller;

import com.neulab.fund.entity.ProductReview;
import com.neulab.fund.service.ProductReviewService;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 产品审核相关接口
 */
@RestController
@RequestMapping("/api/product-reviews")
public class ProductReviewController {
    private final ProductReviewService productReviewService;

    public ProductReviewController(ProductReviewService productReviewService) {
        this.productReviewService = productReviewService;
    }

    /**
     * 查询全部审核记录
     */
    @GetMapping
    public ApiResponse<List<ProductReview>> getAllReviews() {
        return ApiResponse.success(productReviewService.getAllReviews());
    }

    /**
     * 根据ID查询审核记录
     */
    @GetMapping("/{id}")
    public ApiResponse<ProductReview> getReviewById(@PathVariable Long id) {
        return ApiResponse.success(productReviewService.getReviewById(id));
    }

    /**
     * 新增审核记录
     */
    @PostMapping
    public ApiResponse<ProductReview> createReview(@RequestBody ProductReview review) {
        return ApiResponse.success(productReviewService.createReview(review));
    }
} 