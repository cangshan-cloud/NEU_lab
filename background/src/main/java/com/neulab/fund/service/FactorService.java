package com.neulab.fund.service;

import com.neulab.fund.entity.Factor;
import java.util.List;

/**
 * 因子业务接口
 */
public interface FactorService {
    /** 查询全部因子 */
    List<Factor> getAllFactors();
    /** 根据ID查询因子 */
    Factor getFactorById(Long id);
    /** 新增因子 */
    Factor createFactor(Factor factor);
} 