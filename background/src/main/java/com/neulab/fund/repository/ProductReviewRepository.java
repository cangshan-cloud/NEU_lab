package com.neulab.fund.repository;

import com.neulab.fund.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 产品审核数据访问接口
 */
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    /** 根据产品ID查询 */
    List<ProductReview> findByProductId(Long productId);
    
    /** 根据审核状态查询 */
    List<ProductReview> findByReviewStatus(String reviewStatus);
    
    /** 根据审核人ID查询 */
    List<ProductReview> findByReviewerId(Long reviewerId);
    
    /** 根据产品ID查询并按创建时间倒序 */
    List<ProductReview> findByProductIdOrderByCreatedAtDesc(Long productId);
} 