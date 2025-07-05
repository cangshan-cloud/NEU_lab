package com.neulab.fund.repository;

import com.neulab.fund.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 组合产品数据访问接口
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    /** 根据产品类型查询 */
    List<Product> findByProductType(String productType);
    
    /** 根据风险等级查询 */
    List<Product> findByRiskLevel(String riskLevel);
    
    /** 根据策略ID查询 */
    List<Product> findByStrategyId(Long strategyId);
    
    /** 根据组合ID查询 */
    List<Product> findByPortfolioId(Long portfolioId);
} 