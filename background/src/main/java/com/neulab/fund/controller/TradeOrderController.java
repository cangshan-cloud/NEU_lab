package com.neulab.fund.controller;

import com.neulab.fund.entity.TradeOrder;
import com.neulab.fund.service.TradeOrderService;
import com.neulab.fund.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 交易订单Controller
 * 管理交易订单相关接口
 */
@RestController
@RequestMapping("/api/trade-orders")
@Tag(name = "交易订单管理", description = "交易订单相关接口")
public class TradeOrderController {
    
    @Autowired
    private TradeOrderService tradeOrderService;
    
    /**
     * 创建交易订单
     */
    @PostMapping
    @Operation(summary = "创建交易订单", description = "创建新的交易订单")
    public ApiResponse<TradeOrder> createOrder(@RequestBody TradeOrder order) {
        try {
            TradeOrder createdOrder = tradeOrderService.createOrder(order);
            return ApiResponse.success(createdOrder);
        } catch (Exception e) {
            return ApiResponse.error("创建订单失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID查询订单
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询订单", description = "根据订单ID查询订单详情")
    public ApiResponse<TradeOrder> getOrderById(@PathVariable Long id) {
        try {
            return tradeOrderService.findById(id)
                    .map(ApiResponse::success)
                    .orElse(ApiResponse.error("订单不存在"));
        } catch (Exception e) {
            return ApiResponse.error("查询订单失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据订单编号查询订单
     */
    @GetMapping("/order-no/{orderNo}")
    @Operation(summary = "根据订单编号查询订单", description = "根据订单编号查询订单详情")
    public ApiResponse<TradeOrder> getOrderByOrderNo(@PathVariable String orderNo) {
        try {
            return tradeOrderService.findByOrderNo(orderNo)
                    .map(ApiResponse::success)
                    .orElse(ApiResponse.error("订单不存在"));
        } catch (Exception e) {
            return ApiResponse.error("查询订单失败: " + e.getMessage());
        }
    }
    
    /**
     * 查询所有订单
     */
    @GetMapping
    @Operation(summary = "查询所有订单", description = "查询所有交易订单列表")
    public ApiResponse<List<TradeOrder>> getAllOrders() {
        try {
            List<TradeOrder> orders = tradeOrderService.findAll();
            return ApiResponse.success(orders);
        } catch (Exception e) {
            return ApiResponse.error("查询订单列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据用户ID查询订单列表
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "根据用户ID查询订单", description = "根据用户ID查询该用户的订单列表")
    public ApiResponse<List<TradeOrder>> getOrdersByUserId(@PathVariable Long userId) {
        try {
            List<TradeOrder> orders = tradeOrderService.findByUserId(userId);
            return ApiResponse.success(orders);
        } catch (Exception e) {
            return ApiResponse.error("查询用户订单失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据用户ID和状态查询订单列表
     */
    @GetMapping("/user/{userId}/status/{status}")
    @Operation(summary = "根据用户ID和状态查询订单", description = "根据用户ID和订单状态查询订单列表")
    public ApiResponse<List<TradeOrder>> getOrdersByUserIdAndStatus(@PathVariable Long userId, @PathVariable String status) {
        try {
            List<TradeOrder> orders = tradeOrderService.findByUserIdAndStatus(userId, status);
            return ApiResponse.success(orders);
        } catch (Exception e) {
            return ApiResponse.error("查询用户订单失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据产品ID查询订单列表
     */
    @GetMapping("/product/{productId}")
    @Operation(summary = "根据产品ID查询订单", description = "根据产品ID查询相关订单列表")
    public ApiResponse<List<TradeOrder>> getOrdersByProductId(@PathVariable Long productId) {
        try {
            List<TradeOrder> orders = tradeOrderService.findByProductId(productId);
            return ApiResponse.success(orders);
        } catch (Exception e) {
            return ApiResponse.error("查询产品订单失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据状态查询订单列表
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "根据状态查询订单", description = "根据订单状态查询订单列表")
    public ApiResponse<List<TradeOrder>> getOrdersByStatus(@PathVariable String status) {
        try {
            List<TradeOrder> orders = tradeOrderService.findByStatus(status);
            return ApiResponse.success(orders);
        } catch (Exception e) {
            return ApiResponse.error("查询订单失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新订单状态
     */
    @PutMapping("/{id}/status")
    @Operation(summary = "更新订单状态", description = "更新指定订单的状态")
    public ApiResponse<TradeOrder> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            TradeOrder updatedOrder = tradeOrderService.updateOrderStatus(id, status);
            if (updatedOrder != null) {
                return ApiResponse.success(updatedOrder);
            } else {
                return ApiResponse.error("订单不存在");
            }
        } catch (Exception e) {
            return ApiResponse.error("更新订单状态失败: " + e.getMessage());
        }
    }
    
    /**
     * 取消订单
     */
    @PutMapping("/{id}/cancel")
    @Operation(summary = "取消订单", description = "取消指定的交易订单")
    public ApiResponse<TradeOrder> cancelOrder(@PathVariable Long id) {
        try {
            TradeOrder cancelledOrder = tradeOrderService.cancelOrder(id);
            if (cancelledOrder != null) {
                return ApiResponse.success(cancelledOrder);
            } else {
                return ApiResponse.error("订单不存在");
            }
        } catch (Exception e) {
            return ApiResponse.error("取消订单失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除订单
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除订单", description = "删除指定的交易订单")
    public ApiResponse<Void> deleteOrder(@PathVariable Long id) {
        try {
            tradeOrderService.deleteOrder(id);
            return ApiResponse.success(null);
        } catch (Exception e) {
            return ApiResponse.error("删除订单失败: " + e.getMessage());
        }
    }
    
    /**
     * 统计用户订单数量
     */
    @GetMapping("/user/{userId}/count")
    @Operation(summary = "统计用户订单数量", description = "统计指定用户的订单总数")
    public ApiResponse<Long> countByUserId(@PathVariable Long userId) {
        try {
            long count = tradeOrderService.countByUserId(userId);
            return ApiResponse.success(count);
        } catch (Exception e) {
            return ApiResponse.error("统计订单数量失败: " + e.getMessage());
        }
    }
    
    /**
     * 统计用户成功订单数量
     */
    @GetMapping("/user/{userId}/success-count")
    @Operation(summary = "统计用户成功订单数量", description = "统计指定用户的成功订单数量")
    public ApiResponse<Long> countSuccessByUserId(@PathVariable Long userId) {
        try {
            long count = tradeOrderService.countSuccessByUserId(userId);
            return ApiResponse.success(count);
        } catch (Exception e) {
            return ApiResponse.error("统计成功订单数量失败: " + e.getMessage());
        }
    }
} 