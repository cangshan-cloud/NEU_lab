package com.neulab.fund.repository;

import com.neulab.fund.entity.RoleChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface RoleChangeRequestRepository extends JpaRepository<RoleChangeRequest, Long>, JpaSpecificationExecutor<RoleChangeRequest> {
    List<RoleChangeRequest> findByStatus(String status);
    List<RoleChangeRequest> findByUserId(Long userId);
} 