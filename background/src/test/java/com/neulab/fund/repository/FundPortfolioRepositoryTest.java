package com.neulab.fund.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = com.example.background.BackgroundApplication.class)
@ActiveProfiles("test")
class FundPortfolioRepositoryTest {
    @Autowired
    private FundPortfolioRepository fundPortfolioRepository;

    @Test
    void testFindAll() {
        assertNotNull(fundPortfolioRepository.findAll());
    }
} 