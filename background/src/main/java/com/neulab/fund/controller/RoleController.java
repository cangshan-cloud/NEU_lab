package com.neulab.fund.controller;

import com.neulab.fund.entity.Role;
import com.neulab.fund.service.RoleService;
import com.neulab.fund.vo.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 角色管理接口
 */
@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 获取所有角色
     */
    @GetMapping("")
    public ApiResponse listRoles() {
        List<Role> roles = roleService.findAll();
        return ApiResponse.success(roles);
    }
} 