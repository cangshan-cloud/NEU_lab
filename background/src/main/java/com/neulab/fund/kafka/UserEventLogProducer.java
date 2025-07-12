package com.neulab.fund.kafka;

import com.neulab.fund.entity.UserEventLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class UserEventLogProducer {
    private static final String TOPIC = "user-event-log";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public void sendUserEventLog(UserEventLog log) {
        try {
            String message = objectMapper.writeValueAsString(log);
            kafkaTemplate.send(TOPIC, message);
        } catch (Exception e) {
            // 日志记录异常
            e.printStackTrace();
        }
    }
} 