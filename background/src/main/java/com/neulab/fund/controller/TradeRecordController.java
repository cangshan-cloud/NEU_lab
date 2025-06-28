package com.neulab.fund.controller;

import com.neulab.fund.entity.TradeRecord;
import com.neulab.fund.service.TradeRecordService;
import com.neulab.fund.vo.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 交易记录Controller
 * 管理交易记录相关接口
 */
@RestController
@RequestMapping("/api/trade-records")
@Tag(name = "交易记录管理", description = "交易记录相关接口")
public class TradeRecordController {
    
    @Autowired
    private TradeRecordService tradeRecordService;
    
    /**
     * 创建交易记录
     */
    @PostMapping
    @Operation(summary = "创建交易记录", description = "创建新的交易记录")
    public ApiResponse<TradeRecord> createRecord(@RequestBody TradeRecord record) {
        try {
            TradeRecord createdRecord = tradeRecordService.createRecord(record);
            return ApiResponse.success(createdRecord);
        } catch (Exception e) {
            return ApiResponse.error("创建交易记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据ID查询交易记录
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询交易记录", description = "根据记录ID查询交易记录详情")
    public ApiResponse<TradeRecord> getRecordById(@PathVariable Long id) {
        try {
            return tradeRecordService.findById(id)
                    .map(ApiResponse::success)
                    .orElse(ApiResponse.error("交易记录不存在"));
        } catch (Exception e) {
            return ApiResponse.error("查询交易记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据记录编号查询交易记录
     */
    @GetMapping("/record-no/{recordNo}")
    @Operation(summary = "根据记录编号查询交易记录", description = "根据记录编号查询交易记录详情")
    public ApiResponse<TradeRecord> getRecordByRecordNo(@PathVariable String recordNo) {
        try {
            return tradeRecordService.findByRecordNo(recordNo)
                    .map(ApiResponse::success)
                    .orElse(ApiResponse.error("交易记录不存在"));
        } catch (Exception e) {
            return ApiResponse.error("查询交易记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 查询所有交易记录
     */
    @GetMapping
    @Operation(summary = "查询所有交易记录", description = "查询所有交易记录列表")
    public ApiResponse<List<TradeRecord>> getAllRecords() {
        try {
            List<TradeRecord> records = tradeRecordService.findAll();
            return ApiResponse.success(records);
        } catch (Exception e) {
            return ApiResponse.error("查询交易记录列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据订单ID查询交易记录
     */
    @GetMapping("/order/{orderId}")
    @Operation(summary = "根据订单ID查询交易记录", description = "根据订单ID查询相关交易记录")
    public ApiResponse<List<TradeRecord>> getRecordsByOrderId(@PathVariable Long orderId) {
        try {
            List<TradeRecord> records = tradeRecordService.findByOrderId(orderId);
            return ApiResponse.success(records);
        } catch (Exception e) {
            return ApiResponse.error("查询订单交易记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据用户ID查询交易记录
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "根据用户ID查询交易记录", description = "根据用户ID查询该用户的交易记录")
    public ApiResponse<List<TradeRecord>> getRecordsByUserId(@PathVariable Long userId) {
        try {
            List<TradeRecord> records = tradeRecordService.findByUserId(userId);
            return ApiResponse.success(records);
        } catch (Exception e) {
            return ApiResponse.error("查询用户交易记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据用户ID和交易类型查询交易记录
     */
    @GetMapping("/user/{userId}/type/{tradeType}")
    @Operation(summary = "根据用户ID和交易类型查询交易记录", description = "根据用户ID和交易类型查询交易记录")
    public ApiResponse<List<TradeRecord>> getRecordsByUserIdAndType(@PathVariable Long userId, @PathVariable String tradeType) {
        try {
            List<TradeRecord> records = tradeRecordService.findByUserIdAndTradeType(userId, tradeType);
            return ApiResponse.success(records);
        } catch (Exception e) {
            return ApiResponse.error("查询用户交易记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据产品ID查询交易记录
     */
    @GetMapping("/product/{productId}")
    @Operation(summary = "根据产品ID查询交易记录", description = "根据产品ID查询相关交易记录")
    public ApiResponse<List<TradeRecord>> getRecordsByProductId(@PathVariable Long productId) {
        try {
            List<TradeRecord> records = tradeRecordService.findByProductId(productId);
            return ApiResponse.success(records);
        } catch (Exception e) {
            return ApiResponse.error("查询产品交易记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据状态查询交易记录
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "根据状态查询交易记录", description = "根据交易状态查询交易记录")
    public ApiResponse<List<TradeRecord>> getRecordsByStatus(@PathVariable String status) {
        try {
            List<TradeRecord> records = tradeRecordService.findByStatus(status);
            return ApiResponse.success(records);
        } catch (Exception e) {
            return ApiResponse.error("查询交易记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 根据时间范围查询交易记录
     */
    @GetMapping("/time-range")
    @Operation(summary = "根据时间范围查询交易记录", description = "根据时间范围查询交易记录")
    public ApiResponse<List<TradeRecord>> getRecordsByTimeRange(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        try {
            List<TradeRecord> records = tradeRecordService.findByTradeTimeBetween(startTime, endTime);
            return ApiResponse.success(records);
        } catch (Exception e) {
            return ApiResponse.error("查询交易记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新交易记录
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新交易记录", description = "更新指定的交易记录")
    public ApiResponse<TradeRecord> updateRecord(@PathVariable Long id, @RequestBody TradeRecord record) {
        try {
            record.setId(id);
            TradeRecord updatedRecord = tradeRecordService.updateRecord(record);
            return ApiResponse.success(updatedRecord);
        } catch (Exception e) {
            return ApiResponse.error("更新交易记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除交易记录
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除交易记录", description = "删除指定的交易记录")
    public ApiResponse<Void> deleteRecord(@PathVariable Long id) {
        try {
            tradeRecordService.deleteRecord(id);
            return ApiResponse.success(null);
        } catch (Exception e) {
            return ApiResponse.error("删除交易记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 统计用户交易记录数量
     */
    @GetMapping("/user/{userId}/count")
    @Operation(summary = "统计用户交易记录数量", description = "统计指定用户的交易记录总数")
    public ApiResponse<Long> countByUserId(@PathVariable Long userId) {
        try {
            long count = tradeRecordService.countByUserId(userId);
            return ApiResponse.success(count);
        } catch (Exception e) {
            return ApiResponse.error("统计交易记录数量失败: " + e.getMessage());
        }
    }
    
    /**
     * 统计用户成功交易记录数量
     */
    @GetMapping("/user/{userId}/success-count")
    @Operation(summary = "统计用户成功交易记录数量", description = "统计指定用户的成功交易记录数量")
    public ApiResponse<Long> countSuccessByUserId(@PathVariable Long userId) {
        try {
            long count = tradeRecordService.countSuccessByUserId(userId);
            return ApiResponse.success(count);
        } catch (Exception e) {
            return ApiResponse.error("统计成功交易记录数量失败: " + e.getMessage());
        }
    }
} 