package com.neulab.fund.service.impl;

import com.neulab.fund.entity.FactorData;
import com.neulab.fund.repository.FactorDataRepository;
import com.neulab.fund.service.FactorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 因子数据业务实现
 */
@Service
public class FactorDataServiceImpl implements FactorDataService {
    @Autowired
    private FactorDataRepository factorDataRepository;

    @Override
    public FactorData saveFactorData(FactorData factorData) {
        return factorDataRepository.save(factorData);
    }

    @Override
    public void deleteFactorData(Long id) {
        factorDataRepository.deleteById(id);
    }

    @Override
    public FactorData updateFactorData(FactorData factorData) {
        return factorDataRepository.save(factorData);
    }

    @Override
    public FactorData getFactorDataById(Long id) {
        Optional<FactorData> factorData = factorDataRepository.findById(id);
        return factorData.orElse(null);
    }

    @Override
    public List<FactorData> getAllFactorData() {
        return factorDataRepository.findAll();
    }

    @Override
    public List<FactorData> getDataByFactorId(Long factorId) {
        return factorDataRepository.findAll(); // 可根据实际需求自定义查询
    }
} 