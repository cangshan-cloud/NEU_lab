package com.neulab.fund.service;

import com.neulab.fund.entity.FactorData;
import java.util.List;

/**
 * 因子数据业务接口
 */
public interface FactorDataService {
    FactorData saveFactorData(FactorData factorData);
    void deleteFactorData(Long id);
    FactorData updateFactorData(FactorData factorData);
    FactorData getFactorDataById(Long id);
    List<FactorData> getAllFactorData();
    List<FactorData> getDataByFactorId(Long factorId);
} 