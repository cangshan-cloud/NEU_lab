package com.neulab.fund.service;

import com.neulab.fund.entity.FundTag;
import java.util.List;

/**
 * 基金标签业务接口
 */
public interface FundTagService {
    /** 查询全部标签 */
    List<FundTag> getAllTags();
    /** 根据ID查询标签 */
    FundTag getTagById(Long id);
    /** 新增标签 */
    FundTag createTag(FundTag tag);
} 