package com.neulab.fund.repository;

import com.neulab.fund.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void testSaveAndFindById() {
        Product product = new Product();
        product.setName("测试产品");
        product.setDescription("描述");
        product.setRiskLevel("中");
        product.setStrategyId(1L);
        product.setStatus("正常");
        product = productRepository.save(product);
        Optional<Product> found = productRepository.findById(product.getId());
        assertTrue(found.isPresent());
        assertEquals("测试产品", found.get().getName());
    }

    @Test
    void testFindAll() {
        Product product = new Product();
        product.setName("产品A");
        productRepository.save(product);
        List<Product> list = productRepository.findAll();
        assertFalse(list.isEmpty());
    }
} 