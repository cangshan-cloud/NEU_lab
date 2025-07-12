package com.neulab.fund.vo;

import lombok.Data;
import java.util.List;

/**
 * 历史统计数据VO
 */
@Data
public class HistoryAnalyticsVO {
    private List<DayStat> stats;

    @Data
    public static class DayStat {
        private String date; // yyyy-MM-dd
        private int pv;
        private int uv;
    }
} 