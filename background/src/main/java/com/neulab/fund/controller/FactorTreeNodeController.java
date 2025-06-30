package com.neulab.fund.controller;

import com.neulab.fund.entity.FactorTreeNode;
import com.neulab.fund.service.FactorTreeNodeService;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 因子树节点相关接口
 */
@RestController
@RequestMapping("/api/factor-tree-nodes")
public class FactorTreeNodeController {
    @Autowired
    private FactorTreeNodeService factorTreeNodeService;

    /**
     * 新增节点
     */
    @PostMapping
    public ApiResponse<FactorTreeNode> addFactorTreeNode(@RequestBody FactorTreeNode node) {
        return ApiResponse.success(factorTreeNodeService.saveFactorTreeNode(node));
    }

    /**
     * 删除节点
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteFactorTreeNode(@PathVariable Long id) {
        factorTreeNodeService.deleteFactorTreeNode(id);
        return ApiResponse.success();
    }

    /**
     * 更新节点
     */
    @PutMapping
    public ApiResponse<FactorTreeNode> updateFactorTreeNode(@RequestBody FactorTreeNode node) {
        return ApiResponse.success(factorTreeNodeService.updateFactorTreeNode(node));
    }

    /**
     * 根据ID查询节点
     */
    @GetMapping("/{id}")
    public ApiResponse<FactorTreeNode> getFactorTreeNode(@PathVariable Long id) {
        return ApiResponse.success(factorTreeNodeService.getFactorTreeNodeById(id));
    }

    /**
     * 查询全部节点
     */
    @GetMapping
    public ApiResponse<List<FactorTreeNode>> getAllFactorTreeNodes() {
        return ApiResponse.success(factorTreeNodeService.getAllFactorTreeNodes());
    }

    /**
     * 查询某棵树的所有节点
     */
    @GetMapping("/tree/{treeId}")
    public ApiResponse<List<FactorTreeNode>> getNodesByTreeId(@PathVariable Long treeId) {
        return ApiResponse.success(factorTreeNodeService.getNodesByTreeId(treeId));
    }

    /**
     * 批量导入节点
     */
    @PostMapping("/batch")
    public ApiResponse<Void> batchImportNodes(@RequestBody List<FactorTreeNode> nodes) {
        factorTreeNodeService.batchImport(nodes);
        return ApiResponse.success();
    }
} 