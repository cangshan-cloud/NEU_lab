package com.neulab.fund.controller;

import com.neulab.fund.entity.RoleChangeRequest;
import com.neulab.fund.service.RoleChangeRequestService;
import com.neulab.fund.vo.ApiResponse;
import com.neulab.fund.vo.RoleChangeRequestDTO;
import com.neulab.fund.vo.RoleChangeReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * 角色变更申请接口（已废弃，当前系统无角色变更申请功能）
 */
// @RestController
// @RequestMapping("/api/role-change-requests")
// public class RoleChangeRequestController {
//     ... // 全部接口方法注释
// }

@RestController
@RequestMapping("/api/role-change-requests")
public class RoleChangeRequestController {
    @Autowired
    private RoleChangeRequestService service;

    @PostMapping
    public ApiResponse createRequest(@RequestBody RoleChangeRequestDTO dto, @RequestHeader("userId") Long userId) {
        return ApiResponse.success(service.createRequest(userId, dto));
    }

    @GetMapping
    public ApiResponse listRequests(@RequestParam(required = false) String status,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return ApiResponse.success(service.listRequests(status, pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse getRequest(@PathVariable Long id) {
        return ApiResponse.success(service.getRequest(id));
    }

    @PutMapping("/{id}/review")
    public ApiResponse reviewRequest(@PathVariable Long id,
                                     @RequestBody RoleChangeReviewDTO dto,
                                     @RequestHeader("userId") Long reviewerId) {
        return ApiResponse.success(service.reviewRequest(id, reviewerId, dto));
    }
} 