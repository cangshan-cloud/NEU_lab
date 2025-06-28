package com.neulab.fund.controller;

import com.neulab.fund.entity.UserPosition;
import com.neulab.fund.service.UserPositionService;
import com.neulab.fund.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户持仓Controller
 * 管理用户持仓相关接口
 */
@RestController
@RequestMapping("/api/user-positions")
@Tag(name = "用户持仓管理", description = "用户持仓相关接口")
public class UserPositionController {
    
    @Autowired
    private UserPositionService userPositionService;
    
    /**
     * 创建用户持仓
     */
    @PostMapping
    @Operation(summary = "创建用户持仓", description = "创建新的用户持仓记录")
    public ApiResponse<UserPosition> createPosition(@RequestBody UserPosition position) {
        try {
            UserPosition createdPosition = userPositionService.createPosition(position);
            return ApiResponse.success(createdPosition);
        } catch (Exception e) {
            return ApiResponse.error("创建持仓失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID查询持仓
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询持仓", description = "根据持仓ID查询持仓详情")
    public ApiResponse<UserPosition> getPositionById(@PathVariable Long id) {
        try {
            return userPositionService.findById(id)
                    .map(ApiResponse::success)
                    .orElse(ApiResponse.error("持仓不存在"));
        } catch (Exception e) {
            return ApiResponse.error("查询持仓失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据用户ID和产品ID查询持仓
     */
    @GetMapping("/user/{userId}/product/{productId}")
    @Operation(summary = "根据用户ID和产品ID查询持仓", description = "根据用户ID和产品ID查询持仓详情")
    public ApiResponse<UserPosition> getPositionByUserIdAndProductId(@PathVariable Long userId, @PathVariable Long productId) {
        try {
            return userPositionService.findByUserIdAndProductId(userId, productId)
                    .map(ApiResponse::success)
                    .orElse(ApiResponse.error("持仓不存在"));
        } catch (Exception e) {
            return ApiResponse.error("查询持仓失败: " + e.getMessage());
        }
    }
    
    /**
     * 查询所有持仓
     */
    @GetMapping
    @Operation(summary = "查询所有持仓", description = "查询所有用户持仓列表")
    public ApiResponse<List<UserPosition>> getAllPositions() {
        try {
            List<UserPosition> positions = userPositionService.findAll();
            return ApiResponse.success(positions);
        } catch (Exception e) {
            return ApiResponse.error("查询持仓列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据用户ID查询持仓列表
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "根据用户ID查询持仓", description = "根据用户ID查询该用户的持仓列表")
    public ApiResponse<List<UserPosition>> getPositionsByUserId(@PathVariable Long userId) {
        try {
            List<UserPosition> positions = userPositionService.findByUserId(userId);
            return ApiResponse.success(positions);
        } catch (Exception e) {
            return ApiResponse.error("查询用户持仓失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据产品ID查询持仓列表
     */
    @GetMapping("/product/{productId}")
    @Operation(summary = "根据产品ID查询持仓", description = "根据产品ID查询相关持仓列表")
    public ApiResponse<List<UserPosition>> getPositionsByProductId(@PathVariable Long productId) {
        try {
            List<UserPosition> positions = userPositionService.findByProductId(productId);
            return ApiResponse.success(positions);
        } catch (Exception e) {
            return ApiResponse.error("查询产品持仓失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新持仓信息
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新持仓信息", description = "更新指定的持仓信息")
    public ApiResponse<UserPosition> updatePosition(@PathVariable Long id, @RequestBody UserPosition position) {
        try {
            position.setId(id);
            UserPosition updatedPosition = userPositionService.updatePosition(position);
            return ApiResponse.success(updatedPosition);
        } catch (Exception e) {
            return ApiResponse.error("更新持仓失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新持仓份额
     */
    @PutMapping("/user/{userId}/product/{productId}/shares")
    @Operation(summary = "更新持仓份额", description = "更新指定用户的指定产品持仓份额")
    public ApiResponse<UserPosition> updateShares(@PathVariable Long userId, @PathVariable Long productId, @RequestParam BigDecimal shares) {
        try {
            UserPosition updatedPosition = userPositionService.updateShares(userId, productId, shares);
            if (updatedPosition != null) {
                return ApiResponse.success(updatedPosition);
            } else {
                return ApiResponse.error("持仓不存在");
            }
        } catch (Exception e) {
            return ApiResponse.error("更新持仓份额失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新持仓市值
     */
    @PutMapping("/user/{userId}/product/{productId}/market-value")
    @Operation(summary = "更新持仓市值", description = "更新指定用户的指定产品持仓市值")
    public ApiResponse<UserPosition> updateMarketValue(@PathVariable Long userId, @PathVariable Long productId, @RequestParam BigDecimal marketValue) {
        try {
            UserPosition updatedPosition = userPositionService.updateMarketValue(userId, productId, marketValue);
            if (updatedPosition != null) {
                return ApiResponse.success(updatedPosition);
            } else {
                return ApiResponse.error("持仓不存在");
            }
        } catch (Exception e) {
            return ApiResponse.error("更新持仓市值失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除持仓
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除持仓", description = "删除指定的持仓记录")
    public ApiResponse<Void> deletePosition(@PathVariable Long id) {
        try {
            userPositionService.deletePosition(id);
            return ApiResponse.success(null);
        } catch (Exception e) {
            return ApiResponse.error("删除持仓失败: " + e.getMessage());
        }
    }
    
    /**
     * 查询有持仓的用户数量
     */
    @GetMapping("/distinct-users/count")
    @Operation(summary = "查询有持仓的用户数量", description = "统计有持仓的用户总数")
    public ApiResponse<Long> countDistinctUsers() {
        try {
            long count = userPositionService.countDistinctUsers();
            return ApiResponse.success(count);
        } catch (Exception e) {
            return ApiResponse.error("统计用户数量失败: " + e.getMessage());
        }
    }
    
    /**
     * 查询产品持仓用户数量
     */
    @GetMapping("/product/{productId}/count")
    @Operation(summary = "查询产品持仓用户数量", description = "统计指定产品的持仓用户数量")
    public ApiResponse<Long> countByProductId(@PathVariable Long productId) {
        try {
            long count = userPositionService.countByProductId(productId);
            return ApiResponse.success(count);
        } catch (Exception e) {
            return ApiResponse.error("统计产品持仓用户数量失败: " + e.getMessage());
        }
    }
    
    /**
     * 查询用户持仓总市值
     */
    @GetMapping("/user/{userId}/total-market-value")
    @Operation(summary = "查询用户持仓总市值", description = "查询指定用户的持仓总市值")
    public ApiResponse<BigDecimal> sumMarketValueByUserId(@PathVariable Long userId) {
        try {
            BigDecimal totalMarketValue = userPositionService.sumMarketValueByUserId(userId);
            return ApiResponse.success(totalMarketValue);
        } catch (Exception e) {
            return ApiResponse.error("查询持仓总市值失败: " + e.getMessage());
        }
    }
    
    /**
     * 查询用户持仓总成本
     */
    @GetMapping("/user/{userId}/total-cost")
    @Operation(summary = "查询用户持仓总成本", description = "查询指定用户的持仓总成本")
    public ApiResponse<BigDecimal> sumCostByUserId(@PathVariable Long userId) {
        try {
            BigDecimal totalCost = userPositionService.sumCostByUserId(userId);
            return ApiResponse.success(totalCost);
        } catch (Exception e) {
            return ApiResponse.error("查询持仓总成本失败: " + e.getMessage());
        }
    }
    
    /**
     * 查询用户总浮动盈亏
     */
    @GetMapping("/user/{userId}/total-profit-loss")
    @Operation(summary = "查询用户总浮动盈亏", description = "查询指定用户的总浮动盈亏")
    public ApiResponse<BigDecimal> sumProfitLossByUserId(@PathVariable Long userId) {
        try {
            BigDecimal totalProfitLoss = userPositionService.sumProfitLossByUserId(userId);
            return ApiResponse.success(totalProfitLoss);
        } catch (Exception e) {
            return ApiResponse.error("查询总浮动盈亏失败: " + e.getMessage());
        }
    }
} 