package com.neulab.fund.service;

import com.neulab.fund.entity.FundCompany;
import java.util.List;

/**
 * 基金公司业务接口
 */
public interface FundCompanyService {
    /** 查询全部公司 */
    List<FundCompany> getAllCompanies();
    /** 根据ID查询公司 */
    FundCompany getCompanyById(Long id);
    /** 新增公司 */
    FundCompany createCompany(FundCompany company);
} 