package com.neulab.fund.service;

import com.neulab.fund.entity.Product;
import com.neulab.fund.repository.ProductRepository;
import com.neulab.fund.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        // Given
        Product product1 = new Product();
        product1.setId(1L);
        product1.setProductCode("PROD001");
        product1.setProductName("产品1");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setProductCode("PROD002");
        product2.setProductName("产品2");

        List<Product> products = Arrays.asList(product1, product2);
        when(productRepository.findAll()).thenReturn(products);

        // When
        List<Product> result = productService.getAllProducts();

        // Then
        assertEquals(2, result.size());
        assertEquals("产品1", result.get(0).getProductName());
        assertEquals("产品2", result.get(1).getProductName());
    }

    @Test
    void testGetProductById() {
        // Given
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setProductCode("PROD001");
        product.setProductName("测试产品");
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // When
        Product result = productService.getProductById(productId);

        // Then
        assertNotNull(result);
        assertEquals("测试产品", result.getProductName());
        assertEquals("PROD001", result.getProductCode());
    }

    @Test
    void testGetProductByIdNotFound() {
        // Given
        Long productId = 999L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When
        Product result = productService.getProductById(productId);

        // Then
        assertNull(result);
    }

    @Test
    void testCreateProduct() {
        // Given
        Product product = new Product();
        product.setProductCode("PROD001");
        product.setProductName("新产品");
        product.setProductType("股票型");
        product.setRiskLevel("中等风险");

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setProductCode("PROD001");
        savedProduct.setProductName("新产品");
        savedProduct.setProductType("股票型");
        savedProduct.setRiskLevel("中等风险");

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // When
        Product result = productService.createProduct(product);

        // Then
        assertNotNull(result);
        assertEquals("新产品", result.getProductName());
        assertEquals("PROD001", result.getProductCode());
        verify(productRepository).save(product);
    }

    @Test
    void testUpdateProduct() {
        // Given
        Long productId = 1L;
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setProductName("原产品");

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setProductName("更新产品");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // When
        Product result = productService.updateProduct(updatedProduct);

        // Then
        assertNotNull(result);
        assertEquals("更新产品", result.getProductName());
        verify(productRepository).save(updatedProduct);
    }

    @Test
    void testDeleteProduct() {
        // Given
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setProductName("要删除的产品");

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById(productId);

        // When
        productService.deleteProduct(productId);

        // Then
        verify(productRepository).deleteById(productId);
    }
} 