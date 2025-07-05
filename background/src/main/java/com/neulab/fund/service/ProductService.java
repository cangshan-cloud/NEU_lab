package com.neulab.fund.service;

import com.neulab.fund.entity.Product;
import java.util.List;

/**
 * 组合产品业务接口
 */
public interface ProductService {
    /** 查询全部产品 */
    List<Product> getAllProducts();
    /** 根据ID查询产品 */
    Product getProductById(Long id);
    /** 新增产品 */
    Product createProduct(Product product);
    /** 更新产品 */
    Product updateProduct(Product product);
    /** 删除产品 */
    void deleteProduct(Long id);
    /** 根据类型获取产品 */
    List<Product> getProductsByType(String type);
    /** 根据风险等级获取产品 */
    List<Product> getProductsByRiskLevel(String riskLevel);
    /** 根据策略ID获取产品 */
    List<Product> getProductsByStrategyId(Long strategyId);
    /** 根据组合ID获取产品 */
    List<Product> getProductsByPortfolioId(Long portfolioId);
    /**
     * 查询全部产品（VO版）
     */
    java.util.List<com.neulab.fund.vo.ProductVO> getAllProductVOs();
} 