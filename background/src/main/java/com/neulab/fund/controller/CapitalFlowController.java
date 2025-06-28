package com.neulab.fund.controller;

import com.neulab.fund.entity.CapitalFlow;
import com.neulab.fund.service.CapitalFlowService;
import com.neulab.fund.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 资金流水Controller
 * 管理资金流水相关接口
 */
@RestController
@RequestMapping("/api/capital-flows")
@Tag(name = "资金流水管理", description = "资金流水相关接口")
public class CapitalFlowController {
    
    @Autowired
    private CapitalFlowService capitalFlowService;
    
    /**
     * 创建资金流水
     */
    @PostMapping
    @Operation(summary = "创建资金流水", description = "创建新的资金流水记录")
    public ApiResponse<CapitalFlow> createFlow(@RequestBody CapitalFlow flow) {
        try {
            CapitalFlow createdFlow = capitalFlowService.createFlow(flow);
            return ApiResponse.success(createdFlow);
        } catch (Exception e) {
            return ApiResponse.error("创建资金流水失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID查询资金流水
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询资金流水", description = "根据流水ID查询资金流水详情")
    public ApiResponse<CapitalFlow> getFlowById(@PathVariable Long id) {
        try {
            return capitalFlowService.findById(id)
                    .map(ApiResponse::success)
                    .orElse(ApiResponse.error("资金流水不存在"));
        } catch (Exception e) {
            return ApiResponse.error("查询资金流水失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据流水编号查询资金流水
     */
    @GetMapping("/flow-no/{flowNo}")
    @Operation(summary = "根据流水编号查询资金流水", description = "根据流水编号查询资金流水详情")
    public ApiResponse<CapitalFlow> getFlowByFlowNo(@PathVariable String flowNo) {
        try {
            return capitalFlowService.findByFlowNo(flowNo)
                    .map(ApiResponse::success)
                    .orElse(ApiResponse.error("资金流水不存在"));
        } catch (Exception e) {
            return ApiResponse.error("查询资金流水失败: " + e.getMessage());
        }
    }
    
    /**
     * 查询所有资金流水
     */
    @GetMapping
    @Operation(summary = "查询所有资金流水", description = "查询所有资金流水列表")
    public ApiResponse<List<CapitalFlow>> getAllFlows() {
        try {
            List<CapitalFlow> flows = capitalFlowService.findAll();
            return ApiResponse.success(flows);
        } catch (Exception e) {
            return ApiResponse.error("查询资金流水列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据用户ID查询资金流水
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "根据用户ID查询资金流水", description = "根据用户ID查询该用户的资金流水")
    public ApiResponse<List<CapitalFlow>> getFlowsByUserId(@PathVariable Long userId) {
        try {
            List<CapitalFlow> flows = capitalFlowService.findByUserId(userId);
            return ApiResponse.success(flows);
        } catch (Exception e) {
            return ApiResponse.error("查询用户资金流水失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据用户ID和流水类型查询资金流水
     */
    @GetMapping("/user/{userId}/type/{flowType}")
    @Operation(summary = "根据用户ID和流水类型查询资金流水", description = "根据用户ID和流水类型查询资金流水")
    public ApiResponse<List<CapitalFlow>> getFlowsByUserIdAndType(@PathVariable Long userId, @PathVariable String flowType) {
        try {
            List<CapitalFlow> flows = capitalFlowService.findByUserIdAndFlowType(userId, flowType);
            return ApiResponse.success(flows);
        } catch (Exception e) {
            return ApiResponse.error("查询用户资金流水失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据用户ID和状态查询资金流水
     */
    @GetMapping("/user/{userId}/status/{status}")
    @Operation(summary = "根据用户ID和状态查询资金流水", description = "根据用户ID和流水状态查询资金流水")
    public ApiResponse<List<CapitalFlow>> getFlowsByUserIdAndStatus(@PathVariable Long userId, @PathVariable String status) {
        try {
            List<CapitalFlow> flows = capitalFlowService.findByUserIdAndStatus(userId, status);
            return ApiResponse.success(flows);
        } catch (Exception e) {
            return ApiResponse.error("查询用户资金流水失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据订单ID查询资金流水
     */
    @GetMapping("/order/{orderId}")
    @Operation(summary = "根据订单ID查询资金流水", description = "根据订单ID查询相关资金流水")
    public ApiResponse<List<CapitalFlow>> getFlowsByOrderId(@PathVariable Long orderId) {
        try {
            List<CapitalFlow> flows = capitalFlowService.findByOrderId(orderId);
            return ApiResponse.success(flows);
        } catch (Exception e) {
            return ApiResponse.error("查询订单资金流水失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据产品ID查询资金流水
     */
    @GetMapping("/product/{productId}")
    @Operation(summary = "根据产品ID查询资金流水", description = "根据产品ID查询相关资金流水")
    public ApiResponse<List<CapitalFlow>> getFlowsByProductId(@PathVariable Long productId) {
        try {
            List<CapitalFlow> flows = capitalFlowService.findByProductId(productId);
            return ApiResponse.success(flows);
        } catch (Exception e) {
            return ApiResponse.error("查询产品资金流水失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据时间范围查询资金流水
     */
    @GetMapping("/time-range")
    @Operation(summary = "根据时间范围查询资金流水", description = "根据时间范围查询资金流水")
    public ApiResponse<List<CapitalFlow>> getFlowsByTimeRange(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        try {
            List<CapitalFlow> flows = capitalFlowService.findByFlowTimeBetween(startTime, endTime);
            return ApiResponse.success(flows);
        } catch (Exception e) {
            return ApiResponse.error("查询资金流水失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新资金流水
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新资金流水", description = "更新指定的资金流水")
    public ApiResponse<CapitalFlow> updateFlow(@PathVariable Long id, @RequestBody CapitalFlow flow) {
        try {
            flow.setId(id);
            CapitalFlow updatedFlow = capitalFlowService.updateFlow(flow);
            return ApiResponse.success(updatedFlow);
        } catch (Exception e) {
            return ApiResponse.error("更新资金流水失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除资金流水
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除资金流水", description = "删除指定的资金流水")
    public ApiResponse<Void> deleteFlow(@PathVariable Long id) {
        try {
            capitalFlowService.deleteFlow(id);
            return ApiResponse.success(null);
        } catch (Exception e) {
            return ApiResponse.error("删除资金流水失败: " + e.getMessage());
        }
    }
    
    /**
     * 统计用户资金流水数量
     */
    @GetMapping("/user/{userId}/count")
    @Operation(summary = "统计用户资金流水数量", description = "统计指定用户的资金流水总数")
    public ApiResponse<Long> countByUserId(@PathVariable Long userId) {
        try {
            long count = capitalFlowService.countByUserId(userId);
            return ApiResponse.success(count);
        } catch (Exception e) {
            return ApiResponse.error("统计资金流水数量失败: " + e.getMessage());
        }
    }
    
    /**
     * 统计用户成功资金流水数量
     */
    @GetMapping("/user/{userId}/success-count")
    @Operation(summary = "统计用户成功资金流水数量", description = "统计指定用户的成功资金流水数量")
    public ApiResponse<Long> countSuccessByUserId(@PathVariable Long userId) {
        try {
            long count = capitalFlowService.countSuccessByUserId(userId);
            return ApiResponse.success(count);
        } catch (Exception e) {
            return ApiResponse.error("统计成功资金流水数量失败: " + e.getMessage());
        }
    }
    
    /**
     * 查询用户资金流水总金额
     */
    @GetMapping("/user/{userId}/total-amount")
    @Operation(summary = "查询用户资金流水总金额", description = "查询指定用户的资金流水总金额")
    public ApiResponse<BigDecimal> sumAmountByUserId(@PathVariable Long userId) {
        try {
            BigDecimal totalAmount = capitalFlowService.sumAmountByUserId(userId);
            return ApiResponse.success(totalAmount);
        } catch (Exception e) {
            return ApiResponse.error("查询资金流水总金额失败: " + e.getMessage());
        }
    }
    
    /**
     * 查询用户指定类型资金流水总金额
     */
    @GetMapping("/user/{userId}/type/{flowType}/total-amount")
    @Operation(summary = "查询用户指定类型资金流水总金额", description = "查询指定用户的指定类型资金流水总金额")
    public ApiResponse<BigDecimal> sumAmountByUserIdAndFlowType(@PathVariable Long userId, @PathVariable String flowType) {
        try {
            BigDecimal totalAmount = capitalFlowService.sumAmountByUserIdAndFlowType(userId, flowType);
            return ApiResponse.success(totalAmount);
        } catch (Exception e) {
            return ApiResponse.error("查询资金流水总金额失败: " + e.getMessage());
        }
    }
} 