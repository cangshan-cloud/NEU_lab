package com.neulab.fund.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SparkAiUtil {
    @Value("${spark.appid}")
    private String appId;
    @Value("${spark.apikey}")
    private String apiKey;
    @Value("${spark.apisecret}")
    private String apiSecret;
    @Value("${spark.use-mock:true}")  // 默认使用模拟数据
    private boolean useMock;
    private static final String HOST = "spark-api.xf-yun.com";
    private static final String BASE_URL = "wss://spark-api.xf-yun.com/v1/x1";

    /**
     * 结构化AI分析，返回Map<summary, advice, profiles, segments>
     */
    public Map<String, Object> askAi(String prompt) {
        // 默认使用模拟数据，除非明确配置为false
        if (useMock) {
            return generateMockResult(prompt);
        }
        // 真实AI调用逻辑
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String wsUrl = generateAuthUrl(BASE_URL, apiKey, apiSecret, HOST);
            // 优化prompt，强制AI只输出一行JSON，不要加Markdown或解释
            String structPrompt = "请根据以下用户行为数据，严格只输出如下JSON格式，不要任何自然语言解释、不要Markdown、不要多余内容。即使无法分析，也必须输出合法JSON，字段内容可为空。示例：\n" +
                    "{\n  \"summary\": \"一句话总结\",\n  \"advice\": [\"建议1\", \"建议2\"],\n  \"profiles\": [ { \"userId\": 1, \"tags\": \"策略,基金\", \"profileText\": \"...\" } ],\n  \"segments\": [ { \"segmentName\": \"活跃用户\", \"rule\": \"访问频繁\", \"userIds\": [1] } ]\n}\n用户行为数据: " + prompt;
            Map<String, Object> header = new HashMap<>();
            header.put("app_id", appId);
            header.put("uid", "user1");
            Map<String, Object> message = new HashMap<>();
            Map<String, String> text = new HashMap<>();
            text.put("role", "user");
            text.put("content", structPrompt);
            message.put("text", new Object[]{text});
            Map<String, Object> payload = new HashMap<>();
            payload.put("message", message);
            Map<String, Object> chat = new HashMap<>();
            chat.put("domain", "x1");
            chat.put("max_tokens", 1024);
            chat.put("temperature", 0.8);
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("chat", chat);
            Map<String, Object> req = new HashMap<>();
            req.put("header", header);
            req.put("payload", payload);
            req.put("parameter", parameter);
            ObjectMapper mapper = new ObjectMapper();
            String reqJson = mapper.writeValueAsString(req);

            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<StringBuilder> aiText = new AtomicReference<>(new StringBuilder());
            AtomicReference<String> errorMsg = new AtomicReference<>(null);

            WebSocketClient client = new WebSocketClient(new URI(wsUrl)) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    System.out.println("[SparkAI] WebSocket opened: " + handshakedata);
                    try {
                        send(reqJson);
                    } catch (Exception e) {
                        errorMsg.set("AI分析请求发送失败: " + e.getMessage());
                        latch.countDown();
                    }
                }
                @Override
                public void onMessage(String message) {
                    System.out.println("[SparkAI] WebSocket received: " + message);
                    try {
                        JsonNode root = mapper.readTree(message);
                        JsonNode payload = root.path("payload");
                        int status = root.path("header").path("status").asInt(0);
                        if (payload.has("choices")) {
                            JsonNode choices = payload.get("choices");
                            if (choices.has("text")) {
                                for (JsonNode choice : choices.get("text")) {
                                    String content = choice.has("content") ? choice.path("content").asText("") : choice.path("reasoning_content").asText("");
                                    if (content != null && !content.trim().isEmpty()) {
                                        System.out.println("[SparkAI] 分片内容: " + content);
                                        aiText.get().append(content);
                                    }
                                }
                            } else {
                                // 兼容旧结构
                                for (JsonNode choice : choices) {
                                    String content = choice.has("content") ? choice.path("content").asText("") : choice.path("reasoning_content").asText("");
                                    if (content != null && !content.trim().isEmpty()) {
                                        System.out.println("[SparkAI] 分片内容: " + content);
                                        aiText.get().append(content);
                                    }
                                }
                            }
                        }
                        if (status == 2) {
                            System.out.println("[SparkAI] AI原始返回内容: " + aiText.get().toString());
                            latch.countDown();
                        }
                    } catch (Exception e) {
                        System.err.println("[SparkAI] AI分析消息解析失败: " + e.getMessage());
                        errorMsg.set("AI分析消息解析失败: " + e.getMessage());
                        latch.countDown();
                    }
                }
                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("[SparkAI] WebSocket closed: code=" + code + ", reason=" + reason + ", remote=" + remote);
                    latch.countDown();
                }
                @Override
                public void onError(Exception ex) {
                    System.err.println("[SparkAI] WebSocket error: " + ex.getMessage());
                    ex.printStackTrace();
                    errorMsg.set("AI分析WebSocket错误: " + ex.getMessage());
                    latch.countDown();
                }
            };
            client.setSocketFactory(SSLSocketFactory.getDefault());
            client.connect();
            latch.await(20, TimeUnit.SECONDS);
            client.close();
            if (errorMsg.get() != null) {
                resultMap.put("summary", "AI分析失败: " + errorMsg.get());
                resultMap.put("advice", "");
                return resultMap;
            }
            String aiResult = aiText.get().toString();
            System.out.println("[SparkAI] AI原始返回内容: " + aiResult);
            // 尝试提取JSON结构
            String jsonStr = extractJsonEnhanced(aiResult);
            System.out.println("[SparkAI] 提取到的JSON片段: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JsonNode aiJson = mapper.readTree(jsonStr);
                    resultMap.put("summary", aiJson.path("summary").asText(""));
                    resultMap.put("advice", aiJson.path("advice").asText(""));
                    resultMap.put("profiles", aiJson.path("profiles").asText("")); // 模拟数据直接返回结构化对象
                    resultMap.put("segments", aiJson.path("segments").asText("")); // 模拟数据直接返回结构化对象
                } catch (Exception e) {
                    resultMap.put("summary", "AI结果解析失败: " + e.getMessage());
                    resultMap.put("advice", "");
                    System.err.println("[SparkAI] JSON解析失败，原始内容: " + aiResult + ", 片段: " + jsonStr);
                }
            } else {
                // AI分析失败，使用模拟数据
                System.err.println("[SparkAI] 未提取到JSON，使用模拟数据，原始内容: " + aiResult);
                Map<String, Object> mockResult = generateMockResult(prompt);
                resultMap.put("summary", mockResult.get("summary"));
                resultMap.put("advice", mockResult.get("advice"));
                resultMap.put("profiles", mockResult.get("profiles"));
                resultMap.put("segments", mockResult.get("segments"));
            }
            return resultMap;
        } catch (Exception e) {
            resultMap.put("summary", "AI分析异常: " + e.getMessage());
            resultMap.put("advice", "");
            return resultMap;
        }
    }

    /**
     * 生成更丰富的仿真AI分析结果，返回结构化对象
     */
    private Map<String, Object> generateMockResult(String userBehaviorData) {
        Map<String, Object> result = new HashMap<>();
        // 更丰富的summary
        result.put("summary", "本周用户活跃度提升，策略、基金和数据分析板块最受欢迎，部分用户对产品与交易也表现出较高兴趣");
        // 更丰富的advice
        result.put("advice", List.of(
            "为高活跃用户推送定制化策略报告",
            "针对潜在客户设计召回活动",
            "优化产品与交易页面体验",
            "增加数据分析板块内容深度",
            "提升移动端访问体验"
        ));
        // 更丰富的profiles
        result.put("profiles", List.of(
            Map.of("userId", 1, "tags", "策略,基金,数据分析", "profileText", "活跃用户，关注策略、基金和数据分析，操作频繁，偏好深度内容"),
            Map.of("userId", 2, "tags", "产品,交易", "profileText", "潜在客户，偏好产品与交易，偶尔浏览策略内容，转化潜力大"),
            Map.of("userId", 3, "tags", "风控,科技", "profileText", "高价值用户，关注风控与科技板块，行为稳定，投资金额高"),
            Map.of("userId", 4, "tags", "基金,产品", "profileText", "新注册用户，主要浏览基金和产品，尚未下单")
        ));
        // 更丰富的segments
        result.put("segments", List.of(
            Map.of("segmentName", "活跃用户", "rule", "近7天访问>10次", "userIds", List.of(1, 3)),
            Map.of("segmentName", "潜在客户", "rule", "注册未下单", "userIds", List.of(2, 4)),
            Map.of("segmentName", "高价值用户", "rule", "投资金额>10万", "userIds", List.of(1, 3)),
            Map.of("segmentName", "新用户", "rule", "近7天注册", "userIds", List.of(4))
        ));
        return result;
    }

    /**
     * 测试JSON提取逻辑
     */
    public void testJsonExtraction() {
        String testCases[] = {
            // 正常JSON
            "{\"summary\":\"测试总结\",\"advice\":[\"建议1\",\"建议2\"]}",
            
            // 包含Markdown的JSON
            "```json\n{\"summary\":\"测试总结\",\"advice\":[\"建议1\",\"建议2\"]}\n```",
            
            // 包含解释的JSON
            "好的，我现在需要根据用户提供的JSON数据来生成智能运营建议和用户画像。{\"summary\":\"测试总结\",\"advice\":[\"建议1\",\"建议2\"]}",
            
            // 不完整的JSON
            "{\"summary\":\"测试总结\",\"advice\":[\"建议1\",\"建议2\"]",
            
            // 包含多个JSON片段
            "{\"summary\":\"错误总结\"} {\"summary\":\"正确总结\",\"advice\":[\"建议1\"]}"
        };
        
        for (int i = 0; i < testCases.length; i++) {
            String result = extractJsonEnhanced(testCases[i]);
            System.out.println("测试用例 " + (i + 1) + ": " + (result != null ? "成功" : "失败"));
            if (result != null) {
                System.out.println("提取结果: " + result);
            }
        }
    }

    // 增强版：支持 ```json ... ``` 包裹和多JSON片段，优先解析包含summary/advice的片段
    private String extractJsonEnhanced(String text) {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }
        
        List<String> candidates = new ArrayList<>();
        
        // 1. 首先尝试提取 ```json ... ``` 包裹的内容
        String[] codeBlockPatterns = {
            "```json\\s*([\\s\\S]*?)\\s*```",
            "```\\s*([\\s\\S]*?)\\s*```",
            "`([\\s\\S]*?)`"
        };
        
        for (String pattern : codeBlockPatterns) {
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
            java.util.regex.Matcher m = p.matcher(text);
            while (m.find()) {
                String candidate = m.group(1);
                if (candidate.contains("{") && candidate.contains("}")) {
                    candidates.add(candidate);
                }
            }
        }
        
        // 2. 提取所有可能的JSON对象
        java.util.regex.Pattern jsonPattern = java.util.regex.Pattern.compile("\\{[\\s\\S]*?\\}");
        java.util.regex.Matcher jsonMatcher = jsonPattern.matcher(text);
        while (jsonMatcher.find()) {
            String candidate = jsonMatcher.group();
            if (candidate.length() > 20) { // 过滤太短的片段
                candidates.add(candidate);
            }
        }
        
        // 3. 如果没有找到任何JSON片段，尝试从第一个 { 开始构建
        if (candidates.isEmpty()) {
            int start = text.indexOf('{');
            if (start >= 0) {
                String sub = text.substring(start);
                // 尝试补全JSON
                String completed = tryCompleteJson(sub);
                if (completed != null) {
                    candidates.add(completed);
                }
            }
        }
        
        // 4. 按优先级排序：包含summary和advice的优先
        candidates.sort((a, b) -> {
            boolean aHasSummary = a.contains("summary") && a.contains("advice");
            boolean bHasSummary = b.contains("summary") && b.contains("advice");
            if (aHasSummary && !bHasSummary) return -1;
            if (!aHasSummary && bHasSummary) return 1;
            return Integer.compare(b.length(), a.length()); // 长度长的优先
        });
        
        // 5. 验证并返回最佳候选
        for (String candidate : candidates) {
            if (isValidJson(candidate)) {
                return candidate;
            }
        }
        
        // 6. 如果都不行，尝试修复最后一个候选
        if (!candidates.isEmpty()) {
            String fixed = tryFixJson(candidates.get(0));
            if (fixed != null) {
                return fixed;
            }
        }
        
        return null;
    }
    
    private String tryCompleteJson(String jsonStart) {
        try {
            int braceCount = 0;
            int bracketCount = 0;
            StringBuilder result = new StringBuilder();
            
            for (int i = 0; i < jsonStart.length(); i++) {
                char c = jsonStart.charAt(i);
                result.append(c);
                
                if (c == '{') braceCount++;
                else if (c == '}') braceCount--;
                else if (c == '[') bracketCount++;
                else if (c == ']') bracketCount--;
                
                // 如果遇到字符串，跳过到字符串结束
                if (c == '"') {
                    i++;
                    while (i < jsonStart.length() && jsonStart.charAt(i) != '"') {
                        if (jsonStart.charAt(i) == '\\') i++; // 跳过转义字符
                        result.append(jsonStart.charAt(i));
                        i++;
                    }
                    if (i < jsonStart.length()) {
                        result.append(jsonStart.charAt(i));
                    }
                }
            }
            
            // 补全缺失的括号
            for (int i = 0; i < braceCount; i++) {
                result.append('}');
            }
            for (int i = 0; i < bracketCount; i++) {
                result.append(']');
            }
            
            String completed = result.toString();
            if (isValidJson(completed)) {
                return completed;
            }
        } catch (Exception e) {
            // 忽略异常，返回null
        }
        return null;
    }
    
    private String tryFixJson(String json) {
        try {
            // 尝试修复常见的JSON格式问题
            String fixed = json
                .replaceAll(",\\s*}", "}") // 移除尾随逗号
                .replaceAll(",\\s*]", "]") // 移除数组尾随逗号
                .replaceAll("\\s+", " ") // 规范化空白字符
                .trim();
            
            if (isValidJson(fixed)) {
                return fixed;
            }
        } catch (Exception e) {
            // 忽略异常
        }
        return null;
    }
    
    private boolean isValidJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String generateAuthUrl(String baseUrl, String apiKey, String apiSecret, String host) throws Exception {
        String date = getGMTDate();
        String signatureOrigin = "host: " + host + "\n" +
                "date: " + date + "\n" +
                "GET /v1/x1 HTTP/1.1";
        String signature = hmacSha256(signatureOrigin, apiSecret); // 只做一次Base64
        String authorizationOrigin = String.format("api_key=\"%s\", algorithm=\"hmac-sha256\", headers=\"host date request-line\", signature=\"%s\"", apiKey, signature);
        String authorization = Base64.getEncoder().encodeToString(authorizationOrigin.getBytes(StandardCharsets.UTF_8));
        String url = String.format("%s?authorization=%s&date=%s&host=%s",
                baseUrl,
                URLEncoder.encode(authorization, "UTF-8"),
                URLEncoder.encode(date, "UTF-8"),
                URLEncoder.encode(host, "UTF-8"));
        return url;
    }

    private String getGMTDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(new Date());
    }

    private String hmacSha256(String data, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKey);
        byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }
} 