package com.neulab.fund.service.impl;

import com.neulab.fund.entity.FactorTreeNode;
import com.neulab.fund.repository.FactorTreeNodeRepository;
import com.neulab.fund.service.FactorTreeNodeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 因子树节点业务实现
 */
@Service
public class FactorTreeNodeServiceImpl implements FactorTreeNodeService {
    private final FactorTreeNodeRepository factorTreeNodeRepository;

    public FactorTreeNodeServiceImpl(FactorTreeNodeRepository factorTreeNodeRepository) {
        this.factorTreeNodeRepository = factorTreeNodeRepository;
    }

    @Override
    public List<FactorTreeNode> getAllNodes() {
        return factorTreeNodeRepository.findAll();
    }

    @Override
    public FactorTreeNode getNodeById(Long id) {
        return factorTreeNodeRepository.findById(id).orElse(null);
    }

    @Override
    public FactorTreeNode createNode(FactorTreeNode node) {
        return factorTreeNodeRepository.save(node);
    }
} 