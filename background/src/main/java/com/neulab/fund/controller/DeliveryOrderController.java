package com.neulab.fund.controller;

import com.neulab.fund.entity.DeliveryOrder;
import com.neulab.fund.service.DeliveryOrderService;
import com.neulab.fund.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 交割单管理接口
 */
@RestController
@RequestMapping("/api/delivery-orders")
@Tag(name = "交割单管理", description = "交割单多条件筛选")
public class DeliveryOrderController {
    @Autowired
    private DeliveryOrderService deliveryOrderService;

    @GetMapping
    @Operation(summary = "交割单多条件筛选", description = "按用户、产品、状态筛选交割单")
    public ApiResponse<List<DeliveryOrder>> getDeliveryOrders(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) String status) {
        List<DeliveryOrder> orders = deliveryOrderService.findByConditions(userId, productId, status);
        return ApiResponse.success(orders);
    }
} 