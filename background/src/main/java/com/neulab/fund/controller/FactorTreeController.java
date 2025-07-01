package com.neulab.fund.controller;

import com.neulab.fund.entity.FactorTree;
import com.neulab.fund.entity.FactorTreeNode;
import com.neulab.fund.entity.Factor;
import com.neulab.fund.service.FactorTreeService;
import com.neulab.fund.service.FactorTreeNodeService;
import com.neulab.fund.service.FactorService;
import com.neulab.fund.vo.ApiResponse;
import com.neulab.fund.vo.FactorTreeWithNodesVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 因子树相关接口
 */
@RestController
@RequestMapping("/api/factor-trees")
public class FactorTreeController {
    private final FactorTreeService factorTreeService;
    private final FactorTreeNodeService factorTreeNodeService;
    private final FactorService factorService;

    public FactorTreeController(FactorTreeService factorTreeService, FactorTreeNodeService factorTreeNodeService, FactorService factorService) {
        this.factorTreeService = factorTreeService;
        this.factorTreeNodeService = factorTreeNodeService;
        this.factorService = factorService;
    }

    /**
     * 查询全部因子树
     */
    @GetMapping
    public ApiResponse<List<FactorTree>> getAllFactorTrees() {
        return ApiResponse.success(factorTreeService.getAllFactorTrees());
    }

    /**
     * 查询全部因子树及节点和因子
     */
    @GetMapping("/with-nodes")
    public ApiResponse<List<FactorTreeWithNodesVO>> getAllTreesWithNodes() {
        List<FactorTree> trees = factorTreeService.getAllFactorTrees();
        List<FactorTreeWithNodesVO> result = new java.util.ArrayList<>();
        for (FactorTree tree : trees) {
            List<FactorTreeNode> nodes = factorTreeNodeService.getNodesByTreeId(tree.getId());
            List<Factor> factors = new java.util.ArrayList<>();
            for (FactorTreeNode node : nodes) {
                if (node.getFactorId() != null) {
                    Factor f = factorService.getFactorById(node.getFactorId());
                    if (f != null) factors.add(f);
                }
            }
            FactorTreeWithNodesVO vo = new FactorTreeWithNodesVO();
            vo.setTree(tree);
            vo.setNodes(nodes);
            vo.setFactors(factors);
            result.add(vo);
        }
        return ApiResponse.success(result);
    }

    /**
     * 根据ID查询因子树（仅数字ID）
     */
    @GetMapping("/{id:[0-9]+}")
    public ApiResponse<FactorTree> getFactorTreeById(@PathVariable Long id) {
        return ApiResponse.success(factorTreeService.getFactorTreeById(id));
    }

    /**
     * 创建因子树及节点
     */
    @PostMapping("/with-nodes")
    public ApiResponse<Void> createTreeWithNodes(@RequestBody FactorTreeWithNodesVO vo) {
        FactorTree tree = factorTreeService.saveFactorTree(vo.getTree());
        for (FactorTreeNode node : vo.getNodes()) {
            node.setId(null);
            node.setTreeId(tree.getId());
            factorTreeNodeService.saveFactorTreeNode(node);
        }
        return ApiResponse.success();
    }

    /**
     * 编辑因子树及节点（全量覆盖）
     */
    @PutMapping("/with-nodes")
    public ApiResponse<Void> updateTreeWithNodes(@RequestBody FactorTreeWithNodesVO vo) {
        FactorTree tree = factorTreeService.updateFactorTree(vo.getTree());
        // 先删除原有节点
        List<FactorTreeNode> oldNodes = factorTreeNodeService.getNodesByTreeId(tree.getId());
        for (FactorTreeNode node : oldNodes) {
            factorTreeNodeService.deleteFactorTreeNode(node.getId());
        }
        // 新增新节点
        for (FactorTreeNode node : vo.getNodes()) {
            node.setId(null);
            node.setTreeId(tree.getId());
            factorTreeNodeService.saveFactorTreeNode(node);
        }
        return ApiResponse.success();
    }

    /**
     * 删除因子树及其所有节点
     */
    @DeleteMapping("/{id:[0-9]+}")
    public ApiResponse<Void> deleteFactorTree(@PathVariable Long id) {
        // 先删除所有节点
        List<FactorTreeNode> nodes = factorTreeNodeService.getNodesByTreeId(id);
        for (FactorTreeNode node : nodes) {
            factorTreeNodeService.deleteFactorTreeNode(node.getId());
        }
        // 再删除树本身
        factorTreeService.deleteFactorTree(id);
        return ApiResponse.success();
    }
} 