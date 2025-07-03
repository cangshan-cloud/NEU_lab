package com.neulab.fund.service.impl;

import com.neulab.fund.entity.RoleChangeRequest;
import com.neulab.fund.entity.User;
import com.neulab.fund.entity.Role;
import com.neulab.fund.repository.RoleChangeRequestRepository;
import com.neulab.fund.repository.UserRepository;
import com.neulab.fund.repository.RoleRepository;
import com.neulab.fund.service.RoleChangeRequestService;
import com.neulab.fund.vo.RoleChangeRequestDTO;
import com.neulab.fund.vo.RoleChangeReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

/**
 * 角色变更申请服务实现（已废弃）
 */
// @Service
// public class RoleChangeRequestServiceImpl implements RoleChangeRequestService {
//     ... // 全部实现注释
// }

@Service
public class RoleChangeRequestServiceImpl implements RoleChangeRequestService {
    @Autowired
    private RoleChangeRequestRepository requestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleChangeRequest createRequest(Long userId, RoleChangeRequestDTO dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        // 仅普通用户可发起
        Role currentRole = roleRepository.findById(user.getRoleId()).orElse(null);
        if (currentRole == null || !"普通用户".equals(currentRole.getRoleName())) {
            throw new IllegalArgumentException("仅普通用户可发起角色变更申请");
        }
        RoleChangeRequest req = new RoleChangeRequest();
        req.setUserId(userId);
        req.setTargetRoleId(dto.getTargetRoleId());
        req.setReason(dto.getReason());
        req.setStatus("PENDING");
        req.setCreatedAt(LocalDateTime.now());
        req.setUpdatedAt(LocalDateTime.now());
        return requestRepository.save(req);
    }

    @Override
    public Page<RoleChangeRequest> listRequests(String status, Pageable pageable) {
        if (status == null) {
            return requestRepository.findAll(pageable);
        } else {
            return requestRepository.findAll((root, query, cb) -> cb.equal(root.get("status"), status), pageable);
        }
    }

    @Override
    public RoleChangeRequest getRequest(Long id) {
        return requestRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("申请不存在"));
    }

    @Override
    public RoleChangeRequest reviewRequest(Long id, Long reviewerId, RoleChangeReviewDTO dto) {
        RoleChangeRequest req = requestRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("申请不存在"));
        if (!"PENDING".equals(req.getStatus())) {
            throw new IllegalStateException("该申请已处理");
        }
        User reviewer = userRepository.findById(reviewerId).orElseThrow(() -> new IllegalArgumentException("审核人不存在"));
        Role reviewerRole = roleRepository.findById(reviewer.getRoleId()).orElse(null);
        if (reviewerRole == null || (!"管理员".equals(reviewerRole.getRoleName()) && !"超级管理员".equals(reviewerRole.getRoleName()))) {
            throw new IllegalArgumentException("无权限审核");
        }
        req.setStatus(dto.getStatus());
        req.setReviewerId(reviewerId);
        req.setReviewTime(LocalDateTime.now());
        req.setUpdatedAt(LocalDateTime.now());
        // 审批通过则变更用户角色
        if ("APPROVED".equals(dto.getStatus())) {
            User user = userRepository.findById(req.getUserId()).orElseThrow(() -> new IllegalArgumentException("用户不存在"));
            user.setRoleId(req.getTargetRoleId());
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
        }
        return requestRepository.save(req);
    }
} 