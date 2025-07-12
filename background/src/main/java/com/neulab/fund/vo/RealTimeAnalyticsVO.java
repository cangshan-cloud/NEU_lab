package com.neulab.fund.vo;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.util.List;

/**
 * 实时统计数据VO
 */
@Data
public class RealTimeAnalyticsVO {
    private int pv;
    private int uv;
    private int onlineUsers;
    private List<HotPageVO> hotPages;

    @Data
    @AllArgsConstructor
    public static class HotPageVO {
        private String page;
        private int pv;
    }
} 