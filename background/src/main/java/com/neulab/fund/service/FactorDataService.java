package com.neulab.fund.service;

import com.neulab.fund.entity.FactorData;
import java.util.List;

/**
 * 因子数据业务接口
 */
public interface FactorDataService {
    /** 查询全部因子数据 */
    List<FactorData> getAllData();
    /** 根据ID查询因子数据 */
    FactorData getDataById(Long id);
    /** 新增因子数据 */
    FactorData createData(FactorData data);
} 