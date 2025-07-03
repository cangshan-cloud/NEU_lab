package com.neulab.fund.service;

import com.neulab.fund.entity.Role;
import java.util.List;

/**
 * 角色服务接口
 */
public interface RoleService {
    List<Role> findAll();
    Role findByRoleName(String roleName);
    Role findById(Long id);
} 