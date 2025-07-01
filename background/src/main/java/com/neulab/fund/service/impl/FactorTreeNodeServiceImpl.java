package com.neulab.fund.service.impl;

import com.neulab.fund.entity.FactorTreeNode;
import com.neulab.fund.repository.FactorTreeNodeRepository;
import com.neulab.fund.service.FactorTreeNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 因子树节点业务实现
 */
@Service
public class FactorTreeNodeServiceImpl implements FactorTreeNodeService {
    @Autowired
    private FactorTreeNodeRepository factorTreeNodeRepository;

    @Override
    public FactorTreeNode saveFactorTreeNode(FactorTreeNode node) {
        node.setId(null); // 强制新建，防止串号
        return factorTreeNodeRepository.save(node);
    }

    @Override
    public void deleteFactorTreeNode(Long id) {
        factorTreeNodeRepository.deleteById(id);
    }

    @Override
    public FactorTreeNode updateFactorTreeNode(FactorTreeNode node) {
        return factorTreeNodeRepository.save(node);
    }

    @Override
    public FactorTreeNode getFactorTreeNodeById(Long id) {
        Optional<FactorTreeNode> node = factorTreeNodeRepository.findById(id);
        return node.orElse(null);
    }

    @Override
    public List<FactorTreeNode> getAllFactorTreeNodes() {
        return factorTreeNodeRepository.findAll();
    }

    @Override
    public List<FactorTreeNode> getNodesByTreeId(Long treeId) {
        return factorTreeNodeRepository.findByTreeIdOrderByNodeOrder(treeId);
    }

    @Override
    public void batchImport(List<FactorTreeNode> nodes) {
        factorTreeNodeRepository.saveAll(nodes);
    }
} 