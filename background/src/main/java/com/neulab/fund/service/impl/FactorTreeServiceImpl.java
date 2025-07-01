package com.neulab.fund.service.impl;

import com.neulab.fund.entity.FactorTree;
import com.neulab.fund.repository.FactorTreeRepository;
import com.neulab.fund.service.FactorTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 因子树业务实现
 */
@Service
public class FactorTreeServiceImpl implements FactorTreeService {
    @Autowired
    private FactorTreeRepository factorTreeRepository;

    @Override
    public FactorTree saveFactorTree(FactorTree factorTree) {
        return factorTreeRepository.save(factorTree);
    }

    @Override
    public void deleteFactorTree(Long id) {
        factorTreeRepository.deleteById(id);
    }

    @Override
    public FactorTree updateFactorTree(FactorTree factorTree) {
        return factorTreeRepository.save(factorTree);
    }

    @Override
    public FactorTree getFactorTreeById(Long id) {
        Optional<FactorTree> factorTree = factorTreeRepository.findById(id);
        return factorTree.orElse(null);
    }

    @Override
    public List<FactorTree> getAllFactorTrees() {
        return factorTreeRepository.findAll();
    }
} 