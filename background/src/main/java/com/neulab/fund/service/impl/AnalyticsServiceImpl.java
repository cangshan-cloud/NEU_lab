package com.neulab.fund.service.impl;

import com.neulab.fund.entity.UserEventLog;
import com.neulab.fund.repository.UserEventLogRepository;
import com.neulab.fund.service.AnalyticsService;
import com.neulab.fund.vo.RealTimeAnalyticsVO;
import com.neulab.fund.vo.HistoryAnalyticsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    @Autowired
    private UserEventLogRepository userEventLogRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public RealTimeAnalyticsVO getRealtimeAnalytics() {
        // 统计近24小时PV、UV、在线用户、热门页面
        LocalDateTime since = LocalDateTime.now().minusDays(1);
        List<UserEventLog> logs = userEventLogRepository.findByCreatedAtAfter(since);

        int pv = logs.size();
        long uv = logs.stream().map(UserEventLog::getUserId).filter(id -> id != null).distinct().count();
        Map<String, Long> hotPages = logs.stream()
                .filter(log -> log.getPage() != null)
                .collect(Collectors.groupingBy(UserEventLog::getPage, Collectors.counting()));
        List<RealTimeAnalyticsVO.HotPageVO> hotPageList = hotPages.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .map(e -> new RealTimeAnalyticsVO.HotPageVO(e.getKey(), e.getValue().intValue()))
                .collect(Collectors.toList());
        RealTimeAnalyticsVO vo = new RealTimeAnalyticsVO();
        vo.setPv(pv);
        vo.setUv((int) uv);
        vo.setOnlineUsers((int) uv);
        vo.setHotPages(hotPageList);
        return vo;
    }

    @Override
    public HistoryAnalyticsVO getHistoryAnalytics(String start, String end) {
        // 解析日期
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        List<UserEventLog> logs = userEventLogRepository.findByCreatedAtBetween(startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay());
        // 按天分组
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, List<UserEventLog>> byDay = logs.stream().collect(Collectors.groupingBy(
                log -> log.getCreatedAt().toLocalDate().format(fmt),
                LinkedHashMap::new, Collectors.toList()
        ));
        List<HistoryAnalyticsVO.DayStat> stats = byDay.entrySet().stream().map(e -> {
            HistoryAnalyticsVO.DayStat stat = new HistoryAnalyticsVO.DayStat();
            stat.setDate(e.getKey());
            stat.setPv(e.getValue().size());
            stat.setUv((int) e.getValue().stream().map(UserEventLog::getUserId).filter(id -> id != null).distinct().count());
            return stat;
        }).collect(Collectors.toList());
        HistoryAnalyticsVO vo = new HistoryAnalyticsVO();
        vo.setStats(stats);
        return vo;
    }

    @Override
    public String getUserEventStats() {
        try {
            Integer userCount = jdbcTemplate.queryForObject("SELECT COUNT(DISTINCT user_id) FROM user_event_log WHERE user_id IS NOT NULL", Integer.class);
            Integer eventCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_event_log", Integer.class);
            List<Map<String, Object>> topPages = jdbcTemplate.queryForList("SELECT page, COUNT(*) as pv FROM user_event_log GROUP BY page ORDER BY pv DESC LIMIT 3");
            List<String> topPageList = new java.util.ArrayList<>();
            for (Map<String, Object> row : topPages) {
                topPageList.add(row.get("page") + "(" + row.get("pv") + ")");
            }
            List<Long> activeUsers = jdbcTemplate.queryForList("SELECT user_id FROM user_event_log WHERE user_id IS NOT NULL GROUP BY user_id HAVING COUNT(*) > 100", Long.class);
            List<Long> abnormalUsers = jdbcTemplate.queryForList("SELECT DISTINCT user_id FROM user_event_log WHERE event_type='error' AND user_id IS NOT NULL", Long.class);
            Map<String, Object> stats = new java.util.HashMap<>();
            stats.put("userCount", userCount);
            stats.put("eventCount", eventCount);
            stats.put("topPages", topPageList);
            stats.put("activeUserIds", activeUsers);
            stats.put("abnormalUserIds", abnormalUsers);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(stats);
        } catch (Exception e) {
            return "{}";
        }
    }
} 