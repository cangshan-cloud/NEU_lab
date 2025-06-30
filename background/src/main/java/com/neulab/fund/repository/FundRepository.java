package com.neulab.fund.repository;

import com.neulab.fund.entity.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 基金数据访问层
 */
@Repository
public interface FundRepository extends JpaRepository<Fund, Long>, JpaSpecificationExecutor<Fund> {
    
    /**
     * 根据基金代码查询基金
     */
    Optional<Fund> findByCode(String code);
    
    /**
     * 根据基金类型查询基金列表
     */
    List<Fund> findByType(String type);
    
    /**
     * 根据状态查询基金列表
     */
    List<Fund> findByStatus(String status);
    
    /**
     * 根据基金公司ID查询基金列表
     */
    List<Fund> findByCompanyId(Long companyId);
    
    /**
     * 根据基金经理ID查询基金列表
     */
    List<Fund> findByManagerId(Long managerId);
} 