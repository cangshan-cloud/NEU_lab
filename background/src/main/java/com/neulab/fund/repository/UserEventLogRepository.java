package com.neulab.fund.repository;

import com.neulab.fund.entity.UserEventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户行为埋点日志Repository
 */
@Repository
public interface UserEventLogRepository extends JpaRepository<UserEventLog, Long> {
    // 可根据需要扩展自定义查询方法

    /**
     * 查询指定时间后的所有埋点日志
     */
    List<UserEventLog> findByCreatedAtAfter(java.time.LocalDateTime since);

    /**
     * 查询指定时间区间的所有埋点日志
     */
    List<UserEventLog> findByCreatedAtBetween(java.time.LocalDateTime start, java.time.LocalDateTime end);
} 