package com.neulab.fund.repository;

import com.neulab.fund.entity.FactorTree;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 因子树数据访问接口
 */
public interface FactorTreeRepository extends JpaRepository<FactorTree, Long> {
    // 可自定义查询方法
} 