package com.neulab.fund.service.impl;

import com.neulab.fund.entity.FactorTree;
import com.neulab.fund.repository.FactorTreeRepository;
import com.neulab.fund.service.FactorTreeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 因子树业务实现
 */
@Service
public class FactorTreeServiceImpl implements FactorTreeService {
    private final FactorTreeRepository factorTreeRepository;

    public FactorTreeServiceImpl(FactorTreeRepository factorTreeRepository) {
        this.factorTreeRepository = factorTreeRepository;
    }

    @Override
    public List<FactorTree> getAllTrees() {
        return factorTreeRepository.findAll();
    }

    @Override
    public FactorTree getTreeById(Long id) {
        return factorTreeRepository.findById(id).orElse(null);
    }

    @Override
    public FactorTree createTree(FactorTree tree) {
        return factorTreeRepository.save(tree);
    }
} 