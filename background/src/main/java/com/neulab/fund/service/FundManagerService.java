package com.neulab.fund.service;

import com.neulab.fund.entity.FundManager;
import java.util.List;

/**
 * 基金经理业务接口
 */
public interface FundManagerService {
    /** 查询全部经理 */
    List<FundManager> getAllManagers();
    /** 根据ID查询经理 */
    FundManager getManagerById(Long id);
    /** 新增经理 */
    FundManager createManager(FundManager manager);
    /**
     * 查询全部经理（VO版）
     */
    java.util.List<com.neulab.fund.vo.FundManagerVO> getAllManagerVOs();
} 