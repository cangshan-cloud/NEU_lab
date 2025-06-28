package com.neulab.fund.service.impl;

import com.neulab.fund.entity.FundTag;
import com.neulab.fund.repository.FundTagRepository;
import com.neulab.fund.service.FundTagService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 基金标签业务实现
 */
@Service
public class FundTagServiceImpl implements FundTagService {
    private final FundTagRepository fundTagRepository;

    public FundTagServiceImpl(FundTagRepository fundTagRepository) {
        this.fundTagRepository = fundTagRepository;
    }

    @Override
    public List<FundTag> getAllTags() {
        return fundTagRepository.findAll();
    }

    @Override
    public FundTag getTagById(Long id) {
        return fundTagRepository.findById(id).orElse(null);
    }

    @Override
    public FundTag createTag(FundTag tag) {
        return fundTagRepository.save(tag);
    }
} 