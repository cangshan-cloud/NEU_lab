package com.neulab.fund.service;

import com.neulab.fund.entity.RoleChangeRequest;
import com.neulab.fund.vo.RoleChangeRequestDTO;
import com.neulab.fund.vo.RoleChangeReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 角色变更申请服务接口（已废弃）
 */
// public interface RoleChangeRequestService {
//     ... // 全部方法注释
// }

public interface RoleChangeRequestService {
    RoleChangeRequest createRequest(Long userId, RoleChangeRequestDTO dto);
    Page<RoleChangeRequest> listRequests(String status, Pageable pageable);
    RoleChangeRequest getRequest(Long id);
    RoleChangeRequest reviewRequest(Long id, Long reviewerId, RoleChangeReviewDTO dto);
} 