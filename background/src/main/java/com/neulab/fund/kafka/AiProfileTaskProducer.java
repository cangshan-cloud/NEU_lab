package com.neulab.fund.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

@Component
public class AiProfileTaskProducer {
    private static final String TOPIC = "ai-profile-task";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public void sendProfileTask(Long userId, String behaviorJson) {
        System.out.println("【DEBUG】Producer 入参: userId=" + userId + ", behaviorJson=" + behaviorJson);
        try {
            String message = objectMapper.writeValueAsString(Map.of("userId", userId, "behaviorJson", behaviorJson));
            System.out.println("【DEBUG】Producer message 序列化完成: " + message);
            kafkaTemplate.send(TOPIC, message);
            System.out.println("【DEBUG】Producer send 完成");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("【DEBUG】Producer 异常: " + e.getMessage());
        }
    }
} 