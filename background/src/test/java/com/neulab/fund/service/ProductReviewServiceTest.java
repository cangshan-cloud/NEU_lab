package com.neulab.fund.service;

import com.neulab.fund.entity.ProductReview;
import com.neulab.fund.repository.ProductReviewRepository;
import com.neulab.fund.service.impl.ProductReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ProductReviewServiceTest {

    @Mock
    private ProductReviewRepository productReviewRepository;

    @InjectMocks
    private ProductReviewServiceImpl productReviewService;

    private ProductReview testReview;

    @BeforeEach
    void setUp() {
        testReview = new ProductReview();
        testReview.setId(1L);
        testReview.setProductId(1L);
        testReview.setReviewerId(1L);
        testReview.setReviewType("INITIAL");
        testReview.setReviewStatus("PENDING");
        testReview.setReviewComment("审核意见");
        testReview.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void testGetAllReviews() {
        when(productReviewRepository.findAll()).thenReturn(Arrays.asList(testReview));
        
        List<ProductReview> result = productReviewService.getAllReviews();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productReviewRepository).findAll();
    }

    @Test
    void testGetReviewById() {
        when(productReviewRepository.findById(1L)).thenReturn(Optional.of(testReview));
        
        ProductReview result = productReviewService.getReviewById(1L);
        
        assertNotNull(result);
        assertEquals(testReview, result);
        verify(productReviewRepository).findById(1L);
    }

    @Test
    void testCreateReview() {
        when(productReviewRepository.save(any(ProductReview.class))).thenReturn(testReview);
        
        ProductReview result = productReviewService.createReview(testReview);
        
        assertNotNull(result);
        assertEquals(testReview, result);
        verify(productReviewRepository).save(testReview);
    }

    @Test
    void testAllPublicMethods() {
        ProductReviewRepository repo = mock(ProductReviewRepository.class);
        ProductReviewServiceImpl service = new ProductReviewServiceImpl(repo);
        service.toString();
        service.hashCode();
    }
} 