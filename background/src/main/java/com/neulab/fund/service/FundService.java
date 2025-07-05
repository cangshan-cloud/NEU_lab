package com.neulab.fund.service;

import com.neulab.fund.entity.Fund;
import com.neulab.fund.entity.FundCompany;
import com.neulab.fund.entity.FundManager;
import com.neulab.fund.entity.FundTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 基金服务接口
 */
public interface FundService {
    
    /**
     * 获取所有基金
     */
    List<Fund> getAllFunds();
    
    /**
     * 根据条件筛选基金（分页）
     */
    Page<Fund> getFundsWithFilter(Map<String, Object> filters, Pageable pageable);
    
    /**
     * 根据ID获取基金
     */
    Fund getFundById(Long id);
    
    /**
     * 根据基金代码获取基金
     */
    Fund getFundByCode(String fundCode);
    
    /**
     * 创建基金
     */
    Fund createFund(Fund fund);
    
    /**
     * 保存基金
     */
    Fund saveFund(Fund fund);
    
    /**
     * 删除基金
     */
    void deleteFund(Long id);
    
    /**
     * 获取所有基金公司
     */
    List<FundCompany> getAllFundCompanies();
    
    /**
     * 根据ID获取基金公司
     */
    FundCompany getFundCompanyById(Long id);
    
    /**
     * 获取所有基金经理
     */
    List<FundManager> getAllFundManagers();
    
    /**
     * 根据ID获取基金经理
     */
    FundManager getFundManagerById(Long id);
    
    /**
     * 获取所有基金标签
     */
    List<FundTag> getAllFundTags();
    
    /**
     * 获取基金画像
     */
    Map<String, Object> getFundProfile(Long fundId);

    /**
     * 获取所有基金（VO版）
     */
    List<com.neulab.fund.vo.FundListVO> getAllFundVOs();

    /**
     * 根据条件筛选基金（分页，VO版）
     */
    org.springframework.data.domain.Page<com.neulab.fund.vo.FundListVO> getFundVOsWithFilter(Map<String, Object> filters, org.springframework.data.domain.Pageable pageable);
} 