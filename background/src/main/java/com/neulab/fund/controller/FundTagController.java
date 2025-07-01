package com.neulab.fund.controller;

import com.neulab.fund.entity.FundTag;
import com.neulab.fund.repository.FundTagRepository;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/fund-tags")
public class FundTagController {
    private final FundTagRepository fundTagRepository;
    public FundTagController(FundTagRepository fundTagRepository) {
        this.fundTagRepository = fundTagRepository;
    }

    @GetMapping
    public ApiResponse<List<FundTag>> getAllTags() {
        return ApiResponse.success(fundTagRepository.findAll());
    }

    @GetMapping("/all")
    public ApiResponse<List<FundTag>> getAllTagsAll() {
        return ApiResponse.success(fundTagRepository.findAll());
    }

    @PostMapping
    public ApiResponse<FundTag> createTag(@RequestBody FundTag tag) {
        return ApiResponse.success(fundTagRepository.save(tag));
    }

    @PutMapping("/{id}")
    public ApiResponse<FundTag> updateTag(@PathVariable Long id, @RequestBody FundTag tag) {
        tag.setId(id);
        return ApiResponse.success(fundTagRepository.save(tag));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTag(@PathVariable Long id) {
        fundTagRepository.deleteById(id);
        return ApiResponse.success(null);
    }
} 