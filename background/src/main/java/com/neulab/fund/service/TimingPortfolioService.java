package com.neulab.fund.service;

import com.neulab.fund.entity.TimingPortfolio;
import java.util.List;

/**
 * 择时组合业务接口
 */
public interface TimingPortfolioService {
    /** 查询全部择时组合 */
    List<TimingPortfolio> getAllTimingPortfolios();
    /** 根据ID查询择时组合 */
    TimingPortfolio getTimingPortfolioById(Long id);
    /** 新增择时组合 */
    TimingPortfolio createTimingPortfolio(TimingPortfolio timingPortfolio);
} 