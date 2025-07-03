package com.neulab.fund.service;

import com.neulab.fund.repository.ProductRepository;
import com.neulab.fund.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import com.neulab.fund.entity.Product;
import java.util.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testToString() {
        assertDoesNotThrow(() -> productService.toString());
    }

    @Test
    void testGetAllProducts() {
        List<Product> list = Arrays.asList(new Product(), new Product());
        when(productRepository.findAll()).thenReturn(list);
        List<Product> result = productService.getAllProducts();
        assertEquals(2, result.size());
    }

    @Test
    void testGetProductByIdFound() {
        Product product = new Product();
        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Product result = productService.getProductById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetProductByIdNotFound() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());
        Product result = productService.getProductById(2L);
        assertNull(result);
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setName("新产品");
        when(productRepository.save(product)).thenReturn(product);
        Product result = productService.createProduct(product);
        assertEquals("新产品", result.getName());
    }
} 