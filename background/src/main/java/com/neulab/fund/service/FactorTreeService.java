package com.neulab.fund.service;

import com.neulab.fund.entity.FactorTree;
import java.util.List;

/**
 * 因子树业务接口
 */
public interface FactorTreeService {
    FactorTree saveFactorTree(FactorTree factorTree);
    void deleteFactorTree(Long id);
    FactorTree updateFactorTree(FactorTree factorTree);
    FactorTree getFactorTreeById(Long id);
    List<FactorTree> getAllFactorTrees();
} 