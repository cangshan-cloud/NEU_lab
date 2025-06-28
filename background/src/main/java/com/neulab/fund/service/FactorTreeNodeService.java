package com.neulab.fund.service;

import com.neulab.fund.entity.FactorTreeNode;
import java.util.List;

/**
 * 因子树节点业务接口
 */
public interface FactorTreeNodeService {
    /** 查询全部节点 */
    List<FactorTreeNode> getAllNodes();
    /** 根据ID查询节点 */
    FactorTreeNode getNodeById(Long id);
    /** 新增节点 */
    FactorTreeNode createNode(FactorTreeNode node);
} 