package com.neulab.fund.controller;

import com.neulab.fund.service.UserSegmentService;
import com.neulab.fund.vo.ApiResponse;
import com.neulab.fund.vo.UserSegmentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/analytics/segments")
@Tag(name = "用户分群", description = "用户分群接口")
@PreAuthorize("hasRole('ADMIN')")
public class UserSegmentController {
    @Autowired
    private UserSegmentService userSegmentService;

    @GetMapping
    @Operation(summary = "获取用户分群", description = "获取所有用户分群及用户ID列表")
    public ApiResponse<List<UserSegmentVO>> listUserSegments() {
        List<UserSegmentVO> list = userSegmentService.listUserSegments();
        return ApiResponse.success(list);
    }
} 