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
} 