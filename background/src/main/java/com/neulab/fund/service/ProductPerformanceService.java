package com.neulab.fund.service;

import com.neulab.fund.entity.ProductPerformance;
import java.util.List;

/**
 * 产品收益业务接口
 */
public interface ProductPerformanceService {
    /** 查询全部收益记录 */
    List<ProductPerformance> getAllPerformances();
    /** 根据ID查询收益记录 */
    ProductPerformance getPerformanceById(Long id);
    /** 新增收益记录 */
    ProductPerformance createPerformance(ProductPerformance performance);
} 