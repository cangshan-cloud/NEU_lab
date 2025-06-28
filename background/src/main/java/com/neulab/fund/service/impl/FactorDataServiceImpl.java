package com.neulab.fund.service.impl;

import com.neulab.fund.entity.FactorData;
import com.neulab.fund.repository.FactorDataRepository;
import com.neulab.fund.service.FactorDataService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 因子数据业务实现
 */
@Service
public class FactorDataServiceImpl implements FactorDataService {
    private final FactorDataRepository factorDataRepository;

    public FactorDataServiceImpl(FactorDataRepository factorDataRepository) {
        this.factorDataRepository = factorDataRepository;
    }

    @Override
    public List<FactorData> getAllData() {
        return factorDataRepository.findAll();
    }

    @Override
    public FactorData getDataById(Long id) {
        return factorDataRepository.findById(id).orElse(null);
    }

    @Override
    public FactorData createData(FactorData data) {
        return factorDataRepository.save(data);
    }
} 