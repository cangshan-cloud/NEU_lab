package com.neulab.fund.repository;

import com.neulab.fund.entity.FactorTreeNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 因子树节点数据访问层
 */
@Repository
public interface FactorTreeNodeRepository extends JpaRepository<FactorTreeNode, Long> {
    
    /**
     * 根据树ID查询节点，按节点顺序排序
     */
    List<FactorTreeNode> findByTreeIdOrderByNodeOrder(Long treeId);
    
    /**
     * 根据父节点ID查询子节点
     */
    // List<FactorTreeNode> findByParentIdOrderByNodeOrder(Long parentId);
    
    /**
     * 根据树ID和节点类型查询
     */
    List<FactorTreeNode> findByTreeIdAndNodeType(Long treeId, String nodeType);
} 