package com.neulab.fund.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.neulab.fund.entity.UserEventLog;
import com.neulab.fund.service.UserEventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventLogConsumer {
    @Autowired
    private UserEventLogService userEventLogService;

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @KafkaListener(topics = "user-event-log", groupId = "user-event-log-group")
    public void consume(String message) {
        try {
            UserEventLog log = objectMapper.readValue(message, UserEventLog.class);
            userEventLogService.saveEventLog(log);
        } catch (Exception e) {
            // 日志记录异常
            e.printStackTrace();
        }
    }
} 