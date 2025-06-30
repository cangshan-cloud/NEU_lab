package com.neulab.fund.repository;

import com.neulab.fund.entity.FactorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 因子数据访问层
 */
@Repository
public interface FactorDataRepository extends JpaRepository<FactorData, Long> {
    
    /**
     * 根据因子ID查询数据
     */
    List<FactorData> findByFactorId(Long factorId);
    
    /**
     * 根据因子ID和基金ID查询数据
     */
    List<FactorData> findByFactorIdAndFundId(Long factorId, Long fundId);
    
    /**
     * 根据因子ID和日期查询数据
     */
    List<FactorData> findByFactorIdAndDataDate(Long factorId, String dataDate);
} 