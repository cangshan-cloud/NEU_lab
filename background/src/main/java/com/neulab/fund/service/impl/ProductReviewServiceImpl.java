package com.neulab.fund.service.impl;

import com.neulab.fund.entity.ProductReview;
import com.neulab.fund.repository.ProductReviewRepository;
import com.neulab.fund.service.ProductReviewService;
import org.springframework.stereotype.Service;

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
        return productReviewRepository.save(review);
    }
} 