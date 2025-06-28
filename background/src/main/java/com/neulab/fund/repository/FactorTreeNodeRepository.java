package com.neulab.fund.repository;

import com.neulab.fund.entity.FactorTreeNode;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 因子树节点数据访问接口
 */
public interface FactorTreeNodeRepository extends JpaRepository<FactorTreeNode, Long> {
    // 可自定义查询方法
} 