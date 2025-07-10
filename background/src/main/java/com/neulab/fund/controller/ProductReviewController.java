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

    /**
     * 更新审核记录
     */
    @PutMapping("/{id}")
    public ApiResponse<ProductReview> updateReview(@PathVariable Long id, @RequestBody ProductReview review) {
        review.setId(id);
        return ApiResponse.success(productReviewService.updateReview(review));
    }

    /**
     * 删除审核记录
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteReview(@PathVariable Long id) {
        productReviewService.deleteReview(id);
        return ApiResponse.success(null);
    }

    /**
     * 根据产品ID获取审核记录
     */
    @GetMapping("/product/{productId}")
    public ApiResponse<List<ProductReview>> getReviewsByProductId(@PathVariable Long productId) {
        return ApiResponse.success(productReviewService.getReviewsByProductId(productId));
    }

    /**
     * 根据审核状态获取记录
     */
    @GetMapping("/status/{status}")
    public ApiResponse<List<ProductReview>> getReviewsByStatus(@PathVariable String status) {
        return ApiResponse.success(productReviewService.getReviewsByStatus(status));
    }

    /**
     * 根据审核人ID获取记录
     */
    @GetMapping("/reviewer/{reviewerId}")
    public ApiResponse<List<ProductReview>> getReviewsByReviewerId(@PathVariable Long reviewerId) {
        return ApiResponse.success(productReviewService.getReviewsByReviewerId(reviewerId));
    }

    /**
     * 提交产品审核
     */
    @PostMapping("/submit")
    public ApiResponse<ProductReview> submitForReview(@RequestBody ProductReview review) {
        return ApiResponse.success(productReviewService.submitForReview(review));
    }

    /**
     * 审核通过
     */
    @PutMapping("/{id}/approve")
    public ApiResponse<ProductReview> approveReview(@PathVariable Long id, @RequestBody ProductReview review) {
        return ApiResponse.success(productReviewService.approveReview(id, review));
    }

    /**
     * 审核拒绝
     */
    @PutMapping("/{id}/reject")
    public ApiResponse<ProductReview> rejectReview(@PathVariable Long id, @RequestBody ProductReview review) {
        return ApiResponse.success(productReviewService.rejectReview(id, review));
    }

    /**
     * 获取待审核列表
     */
    @GetMapping("/pending")
    public ApiResponse<List<ProductReview>> getPendingReviews() {
        return ApiResponse.success(productReviewService.getPendingReviews());
    }

    /**
     * 获取审核历史
     */
    @GetMapping("/history/{productId}")
    public ApiResponse<List<ProductReview>> getReviewHistory(@PathVariable Long productId) {
        return ApiResponse.success(productReviewService.getReviewHistory(productId));
    }
} 