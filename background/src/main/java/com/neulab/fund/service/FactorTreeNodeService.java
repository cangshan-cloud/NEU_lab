package com.neulab.fund.service;

import com.neulab.fund.entity.FactorTreeNode;
import java.util.List;

/**
 * 因子树节点业务接口
 */
public interface FactorTreeNodeService {
    FactorTreeNode saveFactorTreeNode(FactorTreeNode node);
    void deleteFactorTreeNode(Long id);
    FactorTreeNode updateFactorTreeNode(FactorTreeNode node);
    FactorTreeNode getFactorTreeNodeById(Long id);
    List<FactorTreeNode> getAllFactorTreeNodes();
    List<FactorTreeNode> getNodesByTreeId(Long treeId);
    void batchImport(List<FactorTreeNode> nodes);
} 