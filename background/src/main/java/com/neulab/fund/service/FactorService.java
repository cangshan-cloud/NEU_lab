package com.neulab.fund.service;

import com.neulab.fund.entity.Factor;
import com.neulab.fund.entity.FactorTree;
import com.neulab.fund.entity.FactorTreeNode;
import com.neulab.fund.entity.FactorData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 因子服务接口
 */
public interface FactorService {
    
    /**
     * 获取所有因子
     */
    List<Factor> getAllFactors();
    
    /**
     * 分页获取因子列表
     */
    Page<Factor> getFactorsWithPage(Pageable pageable);
    
    /**
     * 根据ID获取因子
     */
    Factor getFactorById(Long id);
    
    /**
     * 创建因子
     */
    Factor createFactor(Factor factor);
    
    /**
     * 更新因子
     */
    Factor updateFactor(Factor factor);
    
    /**
     * 删除因子
     */
    void deleteFactor(Long id);
    
    /**
     * 获取所有因子树
     */
    List<FactorTree> getAllFactorTrees();
    
    /**
     * 根据ID获取因子树
     */
    FactorTree getFactorTreeById(Long id);
    
    /**
     * 创建因子树
     */
    FactorTree createFactorTree(FactorTree factorTree);
    
    /**
     * 更新因子树
     */
    FactorTree updateFactorTree(FactorTree factorTree);
    
    /**
     * 删除因子树
     */
    void deleteFactorTree(Long id);
    
    /**
     * 获取因子树节点
     */
    List<FactorTreeNode> getFactorTreeNodes(Long treeId);
    
    /**
     * 添加因子树节点
     */
    FactorTreeNode addFactorTreeNode(FactorTreeNode node);
    
    /**
     * 更新因子树节点
     */
    FactorTreeNode updateFactorTreeNode(FactorTreeNode node);
    
    /**
     * 删除因子树节点
     */
    void deleteFactorTreeNode(Long nodeId);
    
    /**
     * 创建衍生因子
     */
    Factor createDerivedFactor(Map<String, Object> factorConfig);
    
    /**
     * 创建风格投资因子
     */
    Factor createStyleFactor(Map<String, Object> factorConfig);
    
    /**
     * 因子数据接入
     */
    boolean importFactorData(Map<String, Object> importConfig);
    
    /**
     * 获取因子数据
     */
    List<FactorData> getFactorData(Long factorId, Long fundId);
    
    /**
     * 保存因子数据
     */
    FactorData saveFactorData(FactorData factorData);
} 