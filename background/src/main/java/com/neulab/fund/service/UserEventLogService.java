package com.neulab.fund.service;

import com.neulab.fund.entity.UserEventLog;

/**
 * 用户行为埋点日志Service接口
 */
public interface UserEventLogService {
    /**
     * 保存埋点日志
     */
    UserEventLog saveEventLog(UserEventLog log);
} 