package com.neulab.fund.controller;

import com.neulab.fund.service.TradeOrderService;
import com.neulab.fund.vo.ApiResponse;
import com.neulab.fund.vo.rebalance.AccountRebalanceRequest;
import com.neulab.fund.vo.rebalance.ErrorHandlingRequest;
import com.neulab.fund.vo.rebalance.RebalanceRequest;
import com.neulab.fund.entity.TradeOrder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 交易管理-调仓/差错/账户调仓相关接口
 */
@RestController
@RequestMapping("/api/trades")
@Tag(name = "交易调仓管理", description = "组合调仓、差错处理、账户调仓接口")
public class TradeRebalanceController {
    @Autowired
    private TradeOrderService tradeOrderService;

    /**
     * 组合调仓：为持有指定组合的所有用户批量生成调仓交易单
     */
    @PostMapping("/rebalance")
    @Operation(summary = "组合调仓", description = "为持有指定组合的所有用户批量生成调仓交易单")
    public ApiResponse<List<TradeOrder>> rebalancePortfolio(@RequestBody RebalanceRequest request) {
        List<TradeOrder> orders = tradeOrderService.rebalancePortfolio(request);
        return ApiResponse.success(orders);
    }

    /**
     * 差错处理：为失败交割单指定替代标的并重新生成交易单
     */
    @PostMapping("/error-handling")
    @Operation(summary = "差错处理", description = "为失败交割单指定替代标的并重新生成交易单")
    public ApiResponse<Void> handleError(@RequestBody ErrorHandlingRequest request) {
        tradeOrderService.handleError(request);
        return ApiResponse.success();
    }

    /**
     * 账户调仓：为指定客户单独调仓，生成调仓交易单
     */
    @PostMapping("/account-rebalance")
    @Operation(summary = "账户调仓", description = "为指定客户单独调仓，生成调仓交易单")
    public ApiResponse<List<TradeOrder>> accountRebalance(@RequestBody AccountRebalanceRequest request) {
        List<TradeOrder> orders = tradeOrderService.accountRebalance(request);
        return ApiResponse.success(orders);
    }

    /**
     * 批量下发交易单
     */
    @PostMapping("/trade-orders/batch-submit")
    @Operation(summary = "批量下发交易单", description = "批量下发选中的交易单")
    public ApiResponse<Void> batchSubmit(@RequestBody List<Long> orderIds) {
        tradeOrderService.batchSubmit(orderIds);
        return ApiResponse.success();
    }

    /**
     * 批量驳回交易单
     */
    @PostMapping("/trade-orders/batch-reject")
    @Operation(summary = "批量驳回交易单", description = "批量驳回选中的交易单")
    public ApiResponse<Void> batchReject(@RequestBody List<Long> orderIds) {
        tradeOrderService.batchReject(orderIds);
        return ApiResponse.success();
    }
} 