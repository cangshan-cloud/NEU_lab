package com.neulab.fund.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.Map;
import com.neulab.fund.entity.UserProfile;
import com.neulab.fund.repository.UserProfileRepository;
import java.time.LocalDateTime;
import com.neulab.fund.util.SparkAiUtil;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class AiProfileTaskConsumer {
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private SparkAiUtil sparkAiUtil;

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    // 假设有一个AI分析服务，实际调用大模型API
    private String analyze(String behaviorJson) {
        String prompt = "请根据以下用户行为数据生成用户画像：" + behaviorJson;
        Map<String, Object> aiResult = sparkAiUtil.askAi(prompt);
        String summary = aiResult.getOrDefault("summary", "").toString();
        String advice = aiResult.getOrDefault("advice", "").toString();
        return summary + "\n" + advice;
    }

    @KafkaListener(topics = "ai-profile-task", groupId = "ai-profile-task-group")
    public void consume(String message) {
        try {
            Map<String, Object> msg = objectMapper.readValue(message, Map.class);
            Long userId = ((Number)msg.get("userId")).longValue();
            String behaviorJson = msg.get("behaviorJson").toString();
            Map<String, Object> aiResult = null;
            String summary = "";
            String advice = "";
            try {
                aiResult = sparkAiUtil.askAi("请根据以下用户行为数据生成用户画像：" + behaviorJson);
                summary = aiResult.getOrDefault("summary", "").toString();
                advice = aiResult.getOrDefault("advice", "").toString();
                // tags多标签结构化处理
                if (advice != null) {
                    advice = advice.trim();
                    if ((advice.startsWith("[") && advice.endsWith("]")) || (advice.startsWith("{") && advice.endsWith("}"))) {
                        try {
                            com.fasterxml.jackson.databind.ObjectMapper om = new com.fasterxml.jackson.databind.ObjectMapper();
                            if (advice.startsWith("[")) {
                                java.util.List<String> tagList = om.readValue(advice, java.util.List.class);
                                advice = String.join(",", tagList);
                            } else {
                                java.util.Map<String, String> tagMap = om.readValue(advice, java.util.Map.class);
                                advice = String.join(",", tagMap.values());
                            }
                        } catch (Exception ignore) {}
                    }
                }
            } catch (Exception e) {
                summary = "AI分析失败: " + e.getMessage();
                advice = "";
            }
            UserProfile profile = userProfileRepository.findByUserId(userId).orElse(new UserProfile());
            profile.setUserId(userId);
            profile.setProfileText(summary);
            profile.setTags(advice);
            profile.setUpdatedAt(LocalDateTime.now());
            userProfileRepository.save(profile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 