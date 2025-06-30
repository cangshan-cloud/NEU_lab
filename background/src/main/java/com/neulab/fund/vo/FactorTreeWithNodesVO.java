package com.neulab.fund.vo;

import com.neulab.fund.entity.FactorTree;
import com.neulab.fund.entity.FactorTreeNode;
import com.neulab.fund.entity.Factor;
import java.util.List;

/**
 * 因子树及其节点和因子VO
 */
public class FactorTreeWithNodesVO {
    private FactorTree tree;
    private List<FactorTreeNode> nodes;
    private List<Factor> factors;

    public FactorTree getTree() { return tree; }
    public void setTree(FactorTree tree) { this.tree = tree; }
    public List<FactorTreeNode> getNodes() { return nodes; }
    public void setNodes(List<FactorTreeNode> nodes) { this.nodes = nodes; }
    public List<Factor> getFactors() { return factors; }
    public void setFactors(List<Factor> factors) { this.factors = factors; }
} 