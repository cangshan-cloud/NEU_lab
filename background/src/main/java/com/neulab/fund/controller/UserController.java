package com.neulab.fund.controller;

import com.neulab.fund.entity.User;
import com.neulab.fund.service.UserService;
import com.neulab.fund.vo.ApiResponse;
import com.neulab.fund.vo.UserRegisterDTO;
import com.neulab.fund.vo.UserLoginDTO;
import com.neulab.fund.vo.UserVO;
import com.neulab.fund.vo.UserUpdateDTO;
import com.neulab.fund.vo.UserResetPasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关接口
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public ApiResponse register(@RequestBody UserRegisterDTO dto) {
        User saved = userService.register(dto);
        return ApiResponse.success(saved);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ApiResponse login(@RequestBody UserLoginDTO dto) {
        UserVO vo = userService.login(dto);
        String token = vo.getStatus();
        vo.setStatus(null); // 清除token在VO中的临时存储
        return ApiResponse.success(new java.util.HashMap<String, Object>() {{
            put("token", token);
            put("user", vo);
        }});
    }

    /**
     * 用户详情
     */
    @GetMapping("/{id}")
    public ApiResponse getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        return ApiResponse.success(userService.toUserVO(user));
    }

    /**
     * 用户信息修改
     */
    @PutMapping("/{id}")
    public ApiResponse updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO dto) {
        UserVO vo = userService.updateUser(id, dto);
        return ApiResponse.success(vo);
    }

    /**
     * 用户分页与筛选
     */
    @GetMapping("")
    public ApiResponse listUsers(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<UserVO> result = userService.listUsers(role, status, keyword, pageable);
        return ApiResponse.success(result);
    }

    /**
     * 绑定基金经理
     */
    @PostMapping("/{id}/bind-manager")
    public ApiResponse bindManager(@PathVariable Long id, @RequestParam Long managerId) {
        return ApiResponse.success(userService.bindManager(id, managerId));
    }

    /**
     * 绑定基金公司
     */
    @PostMapping("/{id}/bind-company")
    public ApiResponse bindCompany(@PathVariable Long id, @RequestParam Long companyId) {
        return ApiResponse.success(userService.bindCompany(id, companyId));
    }

    /**
     * 修改用户角色
     */
    @PutMapping("/{id}/role")
    public ApiResponse updateUserRole(@PathVariable Long id, @RequestParam String roleName) {
        UserVO vo = userService.updateUserRole(id, roleName);
        return ApiResponse.success(vo);
    }

    /**
     * 禁用用户
     */
    @PutMapping("/{id}/disable")
    public ApiResponse disableUser(@PathVariable Long id) {
        UserVO vo = userService.disableUser(id);
        return ApiResponse.success(vo);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public ApiResponse deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponse.success();
    }

    /**
     * 重置用户密码
     */
    @PutMapping("/{id}/password")
    public ApiResponse resetPassword(@PathVariable Long id, @RequestBody UserResetPasswordDTO dto) {
        UserVO vo = userService.resetPassword(id, dto);
        return ApiResponse.success(vo);
    }
} 