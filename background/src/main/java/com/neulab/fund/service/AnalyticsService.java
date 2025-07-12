package com.neulab.fund.service;

import com.neulab.fund.vo.RealTimeAnalyticsVO;

public interface AnalyticsService {
    RealTimeAnalyticsVO getRealtimeAnalytics();

    /**
     * 获取历史统计数据
     */
    com.neulab.fund.vo.HistoryAnalyticsVO getHistoryAnalytics(String start, String end);

    /**
     * 获取用户行为事件统计摘要，供AI分析
     */
    String getUserEventStats();
} 