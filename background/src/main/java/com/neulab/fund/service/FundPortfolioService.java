package com.neulab.fund.service;

import com.neulab.fund.entity.FundPortfolio;
import java.util.List;

/**
 * 基金组合业务接口
 */
public interface FundPortfolioService {
    /** 查询全部组合 */
    List<FundPortfolio> getAllPortfolios();
    /** 根据ID查询组合 */
    FundPortfolio getPortfolioById(Long id);
    /** 新增组合 */
    FundPortfolio createPortfolio(FundPortfolio portfolio);
} 