package com.neulab.fund.repository;

import com.neulab.fund.entity.FactorTree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 因子树数据访问接口
 */
@Repository
public interface FactorTreeRepository extends JpaRepository<FactorTree, Long> {
    // 可自定义查询方法
} 