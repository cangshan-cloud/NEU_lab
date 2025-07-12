package com.neulab.fund.service.impl;

import com.neulab.fund.entity.UserEventLog;
import com.neulab.fund.repository.UserEventLogRepository;
import com.neulab.fund.service.UserEventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户行为埋点日志Service实现
 */
@Service
public class UserEventLogServiceImpl implements UserEventLogService {
    @Autowired
    private UserEventLogRepository userEventLogRepository;

    @Override
    public UserEventLog saveEventLog(UserEventLog log) {
        return userEventLogRepository.save(log);
    }
} 