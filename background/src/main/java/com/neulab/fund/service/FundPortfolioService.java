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
    /** 更新组合 */
    FundPortfolio updatePortfolio(FundPortfolio portfolio);
    /**
     * 查询全部组合（VO版）
     */
    java.util.List<com.neulab.fund.vo.FundPortfolioVO> getAllPortfolioVOs();
    /**
     * 删除组合
     */
    void deletePortfolio(Long id);
} 