package com.neulab.fund.service;

import com.neulab.fund.entity.ProductReview;
import java.util.List;

/**
 * 产品审核业务接口
 */
public interface ProductReviewService {
    /** 查询全部审核记录 */
    List<ProductReview> getAllReviews();
    /** 根据ID查询审核记录 */
    ProductReview getReviewById(Long id);
    /** 新增审核记录 */
    ProductReview createReview(ProductReview review);
    /** 更新审核记录 */
    ProductReview updateReview(ProductReview review);
    /** 删除审核记录 */
    void deleteReview(Long id);
    /** 根据产品ID获取审核记录 */
    List<ProductReview> getReviewsByProductId(Long productId);
    /** 根据审核状态获取记录 */
    List<ProductReview> getReviewsByStatus(String status);
    /** 根据审核人ID获取记录 */
    List<ProductReview> getReviewsByReviewerId(Long reviewerId);
    /** 提交产品审核 */
    ProductReview submitForReview(ProductReview review);
    /** 审核通过 */
    ProductReview approveReview(Long id, ProductReview review);
    /** 审核拒绝 */
    ProductReview rejectReview(Long id, ProductReview review);
    /** 获取待审核列表 */
    List<ProductReview> getPendingReviews();
    /** 获取审核历史 */
    List<ProductReview> getReviewHistory(Long productId);
} 