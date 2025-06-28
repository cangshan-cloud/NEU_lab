package com.neulab.fund.service;

import com.neulab.fund.entity.Fund;
import java.util.List;

/**
 * 基金业务接口
 */
public interface FundService {
    /** 查询全部基金 */
    List<Fund> getAllFunds();
    /** 根据ID查询基金 */
    Fund getFundById(Long id);
    /** 新增基金 */
    Fund createFund(Fund fund);
} 