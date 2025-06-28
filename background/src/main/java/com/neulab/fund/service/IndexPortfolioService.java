package com.neulab.fund.service;

import com.neulab.fund.entity.IndexPortfolio;
import java.util.List;

/**
 * 基金指数组合业务接口
 */
public interface IndexPortfolioService {
    /** 查询全部基金指数组合 */
    List<IndexPortfolio> getAllIndexPortfolios();
    /** 根据ID查询基金指数组合 */
    IndexPortfolio getIndexPortfolioById(Long id);
    /** 新增基金指数组合 */
    IndexPortfolio createIndexPortfolio(IndexPortfolio indexPortfolio);
} 