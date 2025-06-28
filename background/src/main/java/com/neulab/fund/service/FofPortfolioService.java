package com.neulab.fund.service;

import com.neulab.fund.entity.FofPortfolio;
import java.util.List;

/**
 * FOF组合业务接口
 */
public interface FofPortfolioService {
    /** 查询全部FOF组合 */
    List<FofPortfolio> getAllFofPortfolios();
    /** 根据ID查询FOF组合 */
    FofPortfolio getFofPortfolioById(Long id);
    /** 新增FOF组合 */
    FofPortfolio createFofPortfolio(FofPortfolio fofPortfolio);
} 