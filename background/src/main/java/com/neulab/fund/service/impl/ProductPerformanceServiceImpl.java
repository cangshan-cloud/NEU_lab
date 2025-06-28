package com.neulab.fund.service.impl;

import com.neulab.fund.entity.ProductPerformance;
import com.neulab.fund.repository.ProductPerformanceRepository;
import com.neulab.fund.service.ProductPerformanceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品收益业务实现
 */
@Service
public class ProductPerformanceServiceImpl implements ProductPerformanceService {
    private final ProductPerformanceRepository productPerformanceRepository;

    public ProductPerformanceServiceImpl(ProductPerformanceRepository productPerformanceRepository) {
        this.productPerformanceRepository = productPerformanceRepository;
    }

    @Override
    public List<ProductPerformance> getAllPerformances() {
        return productPerformanceRepository.findAll();
    }

    @Override
    public ProductPerformance getPerformanceById(Long id) {
        return productPerformanceRepository.findById(id).orElse(null);
    }

    @Override
    public ProductPerformance createPerformance(ProductPerformance performance) {
        return productPerformanceRepository.save(performance);
    }
} 