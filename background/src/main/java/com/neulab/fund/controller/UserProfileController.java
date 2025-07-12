package com.neulab.fund.controller;

import com.neulab.fund.service.UserProfileService;
import com.neulab.fund.vo.ApiResponse;
import com.neulab.fund.vo.UserProfileVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-profile")
@Tag(name = "用户画像", description = "用户画像接口")
@PreAuthorize("hasRole('ADMIN')")
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/{userId}")
    @Operation(summary = "获取用户画像", description = "根据用户ID获取画像")
    public ApiResponse<UserProfileVO> getUserProfile(@PathVariable Long userId) {
        UserProfileVO vo = userProfileService.getUserProfile(userId);
        return ApiResponse.success(vo);
    }
} 