package com.neulab.fund.service;

import com.neulab.fund.entity.FactorTree;
import java.util.List;

/**
 * 因子树业务接口
 */
public interface FactorTreeService {
    /** 查询全部因子树 */
    List<FactorTree> getAllTrees();
    /** 根据ID查询因子树 */
    FactorTree getTreeById(Long id);
    /** 新增因子树 */
    FactorTree createTree(FactorTree tree);
} 