package com.neulab.fund.controller;

import com.neulab.fund.entity.FactorTreeNode;
import com.neulab.fund.service.FactorTreeNodeService;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 因子树节点相关接口
 */
@RestController
@RequestMapping("/api/factor-tree-nodes")
public class FactorTreeNodeController {
    private final FactorTreeNodeService factorTreeNodeService;

    public FactorTreeNodeController(FactorTreeNodeService factorTreeNodeService) {
        this.factorTreeNodeService = factorTreeNodeService;
    }

    /**
     * 查询全部节点
     */
    @GetMapping
    public ApiResponse<List<FactorTreeNode>> getAllNodes() {
        return ApiResponse.success(factorTreeNodeService.getAllNodes());
    }

    /**
     * 根据ID查询节点
     */
    @GetMapping("/{id}")
    public ApiResponse<FactorTreeNode> getNodeById(@PathVariable Long id) {
        return ApiResponse.success(factorTreeNodeService.getNodeById(id));
    }

    /**
     * 新增节点
     */
    @PostMapping
    public ApiResponse<FactorTreeNode> createNode(@RequestBody FactorTreeNode node) {
        return ApiResponse.success(factorTreeNodeService.createNode(node));
    }
} 