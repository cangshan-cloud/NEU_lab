package com.neulab.fund.controller;

import com.neulab.fund.entity.UserEventLog;
import com.neulab.fund.service.UserEventLogService;
import com.neulab.fund.vo.ApiResponse;
import com.neulab.fund.kafka.UserEventLogProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 用户行为埋点日志Controller
 * 接收前端埋点数据
 */
@RestController
@RequestMapping("/api/track-events")
@Tag(name = "用户行为埋点", description = "前端埋点数据接收接口")
public class UserEventLogController {
    @Autowired
    private UserEventLogService userEventLogService;
    @Autowired
    private UserEventLogProducer userEventLogProducer;

    @PostMapping
    @Operation(summary = "接收埋点数据", description = "接收前端用户行为埋点日志")
    public ApiResponse<String> trackEvent(@RequestBody UserEventLog log, HttpServletRequest request) {
        log.setCreatedAt(LocalDateTime.now());
        // 自动填充IP和UA
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        log.setIpAddress(ip);
        log.setUserAgent(request.getHeader("User-Agent"));
        userEventLogProducer.sendUserEventLog(log);
        return ApiResponse.success("ok");
    }
} 