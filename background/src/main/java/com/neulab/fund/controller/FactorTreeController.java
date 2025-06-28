package com.neulab.fund.controller;

import com.neulab.fund.entity.FactorTree;
import com.neulab.fund.service.FactorTreeService;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 因子树相关接口
 */
@RestController
@RequestMapping("/api/factor-trees")
public class FactorTreeController {
    private final FactorTreeService factorTreeService;

    public FactorTreeController(FactorTreeService factorTreeService) {
        this.factorTreeService = factorTreeService;
    }

    /**
     * 查询全部因子树
     */
    @GetMapping
    public ApiResponse<List<FactorTree>> getAllTrees() {
        return ApiResponse.success(factorTreeService.getAllTrees());
    }

    /**
     * 根据ID查询因子树
     */
    @GetMapping("/{id}")
    public ApiResponse<FactorTree> getTreeById(@PathVariable Long id) {
        return ApiResponse.success(factorTreeService.getTreeById(id));
    }

    /**
     * 新增因子树
     */
    @PostMapping
    public ApiResponse<FactorTree> createTree(@RequestBody FactorTree tree) {
        return ApiResponse.success(factorTreeService.createTree(tree));
    }
} 