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
} 