package com.neulab.fund.service.impl;

import com.neulab.fund.entity.Product;
import com.neulab.fund.repository.ProductRepository;
import com.neulab.fund.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组合产品业务实现
 */
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getProductsByType(String type) {
        return productRepository.findByProductType(type);
    }

    @Override
    public List<Product> getProductsByRiskLevel(String riskLevel) {
        return productRepository.findByRiskLevel(riskLevel);
    }

    @Override
    public List<Product> getProductsByStrategyId(Long strategyId) {
        return productRepository.findByStrategyId(strategyId);
    }

    @Override
    public List<Product> getProductsByPortfolioId(Long portfolioId) {
        return productRepository.findByPortfolioId(portfolioId);
    }

    @Override
    public java.util.List<com.neulab.fund.vo.ProductVO> getAllProductVOs() {
        java.util.List<Product> productList = productRepository.findAll();
        java.util.List<com.neulab.fund.vo.ProductVO> voList = new java.util.ArrayList<>();
        for (Product product : productList) {
            com.neulab.fund.vo.ProductVO vo = new com.neulab.fund.vo.ProductVO();
            vo.setId(product.getId());
            vo.setProductCode(product.getProductCode());
            vo.setProductName(product.getProductName());
            vo.setProductType(product.getProductType());
            vo.setStrategyId(product.getStrategyId());
            vo.setPortfolioId(product.getPortfolioId());
            vo.setRiskLevel(product.getRiskLevel());
            vo.setTargetReturn(product.getTargetReturn());
            vo.setMaxDrawdown(product.getMaxDrawdown());
            vo.setInvestmentHorizon(product.getInvestmentHorizon());
            vo.setMinInvestment(product.getMinInvestment());
            vo.setMaxInvestment(product.getMaxInvestment());
            vo.setManagementFee(product.getManagementFee());
            vo.setPerformanceFee(product.getPerformanceFee());
            vo.setSubscriptionFee(product.getSubscriptionFee());
            vo.setRedemptionFee(product.getRedemptionFee());
            vo.setDescription(product.getDescription());
            vo.setProspectus(product.getProspectus());
            vo.setStatus(product.getStatus());
            vo.setCreatedAt(product.getCreatedAt() != null ? product.getCreatedAt().toString() : null);
            vo.setUpdatedAt(product.getUpdatedAt() != null ? product.getUpdatedAt().toString() : null);
            voList.add(vo);
        }
        return voList;
    }
} 