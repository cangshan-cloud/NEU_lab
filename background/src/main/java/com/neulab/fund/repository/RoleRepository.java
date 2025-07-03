package com.neulab.fund.repository;

import com.neulab.fund.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 角色数据访问层
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
} 