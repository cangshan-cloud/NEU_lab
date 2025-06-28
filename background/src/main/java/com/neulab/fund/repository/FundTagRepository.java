package com.neulab.fund.repository;

import com.neulab.fund.entity.FundTag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 基金标签数据访问接口
 */
public interface FundTagRepository extends JpaRepository<FundTag, Long> {
    // 可自定义查询方法
} 