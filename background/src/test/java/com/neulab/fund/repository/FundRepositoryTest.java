package com.neulab.fund.repository;

import com.neulab.fund.entity.Fund;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FundRepositoryTest {
    @Autowired
    private FundRepository fundRepository;

    @Test
    void testFindByCode() {
        Fund fund = new Fund();
        fund.setCode("F123");
        fund.setName("测试基金");
        fund = fundRepository.save(fund);
        Optional<Fund> found = fundRepository.findByCode("F123");
        assertTrue(found.isPresent());
        assertEquals("测试基金", found.get().getName());
    }

    @Test
    void testFindByType() {
        Fund fund = new Fund();
        fund.setType("股票型");
        fundRepository.save(fund);
        List<Fund> list = fundRepository.findByType("股票型");
        assertFalse(list.isEmpty());
    }

    @Test
    void testFindByStatus() {
        Fund fund = new Fund();
        fund.setStatus("正常");
        fundRepository.save(fund);
        List<Fund> list = fundRepository.findByStatus("正常");
        assertFalse(list.isEmpty());
    }

    @Test
    void testFindByCompanyId() {
        Fund fund = new Fund();
        fund.setCompanyId(100L);
        fundRepository.save(fund);
        List<Fund> list = fundRepository.findByCompanyId(100L);
        assertFalse(list.isEmpty());
    }

    @Test
    void testFindByManagerId() {
        Fund fund = new Fund();
        fund.setManagerId(200L);
        fundRepository.save(fund);
        List<Fund> list = fundRepository.findByManagerId(200L);
        assertFalse(list.isEmpty());
    }
} 