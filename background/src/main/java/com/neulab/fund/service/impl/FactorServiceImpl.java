package com.neulab.fund.service.impl;

import com.neulab.fund.entity.Factor;
import com.neulab.fund.repository.FactorRepository;
import com.neulab.fund.service.FactorService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 因子业务实现
 */
@Service
public class FactorServiceImpl implements FactorService {
    private final FactorRepository factorRepository;

    public FactorServiceImpl(FactorRepository factorRepository) {
        this.factorRepository = factorRepository;
    }

    @Override
    public List<Factor> getAllFactors() {
        return factorRepository.findAll();
    }

    @Override
    public Factor getFactorById(Long id) {
        return factorRepository.findById(id).orElse(null);
    }

    @Override
    public Factor createFactor(Factor factor) {
        return factorRepository.save(factor);
    }
} 