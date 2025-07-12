package com.neulab.fund.service.impl;

import com.neulab.fund.entity.ProductReview;
import com.neulab.fund.repository.ProductReviewRepository;
import com.neulab.fund.service.ProductReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 产品审核业务实现
 */
@Service
public class ProductReviewServiceImpl implements ProductReviewService {
    private final ProductReviewRepository productReviewRepository;

    public ProductReviewServiceImpl(ProductReviewRepository productReviewRepository) {
        this.productReviewRepository = productReviewRepository;
    }

    @Override
    public List<ProductReview> getAllReviews() {
        return productReviewRepository.findAll();
    }

    @Override
    public ProductReview getReviewById(Long id) {
        return productReviewRepository.findById(id).orElse(null);
    }

    @Override
    public ProductReview createReview(ProductReview review) {
        // 不设置 createdAt，数据库自动填充
        review.setCreatedAt(null);
        return productReviewRepository.save(review);
    }

    @Override
    public ProductReview updateReview(ProductReview review) {
        return productReviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long id) {
        productReviewRepository.deleteById(id);
    }

    @Override
    public List<ProductReview> getReviewsByProductId(Long productId) {
        return productReviewRepository.findByProductId(productId);
    }

    @Override
    public List<ProductReview> getReviewsByStatus(String status) {
        return productReviewRepository.findByReviewStatus(status);
    }

    @Override
    public List<ProductReview> getReviewsByReviewerId(Long reviewerId) {
        return productReviewRepository.findByReviewerId(reviewerId);
    }

    @Override
    public ProductReview submitForReview(ProductReview review) {
        review.setReviewStatus("PENDING");
        review.setUpdatedAt(LocalDateTime.now());
        return productReviewRepository.save(review);
    }

    @Override
    public ProductReview approveReview(Long id, ProductReview review) {
        ProductReview existingReview = productReviewRepository.findById(id).orElse(null);
        if (existingReview != null) {
            existingReview.setReviewStatus("APPROVED");
            existingReview.setReviewComment(review.getReviewComment());
            existingReview.setReviewDate(LocalDateTime.now());
            existingReview.setUpdatedAt(LocalDateTime.now());
            return productReviewRepository.save(existingReview);
        }
        return null;
    }

    @Override
    public ProductReview rejectReview(Long id, ProductReview review) {
        ProductReview existingReview = productReviewRepository.findById(id).orElse(null);
        if (existingReview != null) {
            existingReview.setReviewStatus("REJECTED");
            existingReview.setReviewComment(review.getReviewComment());
            existingReview.setReviewDate(LocalDateTime.now());
            existingReview.setUpdatedAt(LocalDateTime.now());
            return productReviewRepository.save(existingReview);
        }
        return null;
    }

    @Override
    public List<ProductReview> getPendingReviews() {
        return productReviewRepository.findByReviewStatus("PENDING");
    }

    @Override
    public List<ProductReview> getReviewHistory(Long productId) {
        return productReviewRepository.findByProductIdOrderByCreatedAtDesc(productId);
    }
} 