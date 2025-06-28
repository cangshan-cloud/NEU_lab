package com.neulab.fund.repository;

import com.neulab.fund.entity.FundCompany;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 基金公司数据访问接口
 */
public interface FundCompanyRepository extends JpaRepository<FundCompany, Long> {
    // 可自定义查询方法
} 