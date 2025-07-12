package com.neulab.fund.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.neulab.fund.service.AnalyticsService;
import com.neulab.fund.vo.ApiResponse;
import com.neulab.fund.vo.HistoryAnalyticsVO;
import com.neulab.fund.vo.RealTimeAnalyticsVO;
import com.neulab.fund.util.SparkAiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * 数据分析相关接口
 */
@RestController
@RequestMapping("/api/analytics")
@Tag(name = "数据分析", description = "埋点数据分析接口")
@PreAuthorize("hasRole('ADMIN')")
public class AnalyticsController {
    @Autowired
    private AnalyticsService analyticsService;

    @Autowired
    private SparkAiUtil sparkAiUtil;

    // 内存任务结果存储（演示用，生产建议用Redis）
    private static final ConcurrentHashMap<String, Map<String, Object>> llmSummaryResultMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, String> llmSummaryStatusMap = new ConcurrentHashMap<>();

    @GetMapping("/realtime")
    @Operation(summary = "实时统计", description = "获取实时PV、UV、在线用户、热门页面等数据")
    public ApiResponse<RealTimeAnalyticsVO> getRealtimeAnalytics() {
        RealTimeAnalyticsVO data = analyticsService.getRealtimeAnalytics();
        return ApiResponse.success(data);
    }

    @GetMapping("/overview")
    @Operation(summary = "历史统计", description = "获取历史区间内每日PV、UV等数据")
    public ApiResponse<HistoryAnalyticsVO> getHistoryAnalytics(@RequestParam String start, @RequestParam String end) {
        HistoryAnalyticsVO data = analyticsService.getHistoryAnalytics(start, end);
        return ApiResponse.success(data);
    }

    @PostMapping("/llm-summary/async")
    @Operation(summary = "异步发起大模型行为洞察分析", description = "立即返回任务ID，前端轮询查结果")
    public ApiResponse<String> asyncLlmSummary() {
        String taskId = UUID.randomUUID().toString();
        llmSummaryStatusMap.put(taskId, "processing");
        new Thread(() -> {
            try {
                String stats = analyticsService.getUserEventStats();
                String prompt = "请基于以下用户行为数据，严格只输出如下JSON结构，不要任何自然语言解释、不要Markdown、不要多余内容。即使无法分析，也必须输出合法JSON，字段内容可为空。示例：\n" +
                        "{\n  \"summary\": \"一句话总结\",\n  \"advice\": [\"建议1\", \"建议2\"],\n  \"profiles\": [ { \"userId\": 1, \"tags\": \"策略,基金\", \"profileText\": \"...\" } ],\n  \"segments\": [ { \"segmentName\": \"活跃用户\", \"rule\": \"访问频繁\", \"userIds\": [1] } ]\n}\n用户行为数据：" + stats;
                Map<String, Object> aiResultRaw = sparkAiUtil.askAi(prompt);
                // 直接存结构化对象
                llmSummaryResultMap.put(taskId, aiResultRaw);
                llmSummaryStatusMap.put(taskId, "done");
            } catch (Exception e) {
                Map<String, Object> err = new HashMap<>();
                err.put("summary", "AI分析异常: " + e.getMessage());
                err.put("advice", List.of());
                err.put("profiles", List.of());
                err.put("segments", List.of());
                llmSummaryResultMap.put(taskId, err);
                llmSummaryStatusMap.put(taskId, "error");
            }
        }).start();
        return ApiResponse.success(taskId);
    }

    @GetMapping("/llm-summary/result")
    @Operation(summary = "查询大模型行为洞察分析结果", description = "根据任务ID轮询查AI分析结果")
    public ApiResponse<Object> getLlmSummaryResult(@RequestParam String taskId) {
        String status = llmSummaryStatusMap.get(taskId);
        if (status == null) {
            return ApiResponse.error("任务不存在或已过期");
        }
        if ("processing".equals(status)) {
            return ApiResponse.success(Map.of("status", "processing"));
        }
        Map<String, Object> result = llmSummaryResultMap.get(taskId);
        return ApiResponse.success(Map.of("status", status, "result", result));
    }

    @GetMapping("/test-json-extraction")
    @Operation(summary = "测试JSON提取逻辑", description = "测试AI返回内容的JSON提取功能")
    public ApiResponse<String> testJsonExtraction() {
        try {
            sparkAiUtil.testJsonExtraction();
            return ApiResponse.success("JSON提取测试完成，请查看控制台日志");
        } catch (Exception e) {
            return ApiResponse.error("测试失败: " + e.getMessage());
        }
    }

    @PostMapping("/test-mock-analysis")
    @Operation(summary = "测试模拟数据分析", description = "使用模拟数据测试AI分析功能")
    public ApiResponse<Object> testMockAnalysis(@RequestBody String userBehaviorData) {
        try {
            Map<String, Object> result = sparkAiUtil.askAi(userBehaviorData);
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("测试失败: " + e.getMessage());
        }
    }

    @PostMapping("/switch-ai-mode")
    @Operation(summary = "切换AI分析模式", description = "在真实AI分析和模拟数据之间切换")
    public ApiResponse<String> switchAiMode(@RequestParam boolean useMock) {
        try {
            // 通过反射设置useMock字段
            java.lang.reflect.Field field = sparkAiUtil.getClass().getDeclaredField("useMock");
            field.setAccessible(true);
            field.set(sparkAiUtil, useMock);
            
            String mode = useMock ? "模拟数据" : "真实AI分析";
            return ApiResponse.success("已切换到" + mode + "模式");
        } catch (Exception e) {
            return ApiResponse.error("切换失败: " + e.getMessage());
        }
    }

    @GetMapping("/test-data-example")
    @Operation(summary = "获取测试数据示例", description = "返回用于测试的用户行为数据示例")
    public ApiResponse<Object> getTestDataExample() {
        Map<String, Object> example = new HashMap<>();
        example.put("topPages", java.util.Arrays.asList(
            Map.of("page", "/admin/analytics", "count", 121),
            Map.of("page", "/strategies", "count", 109),
            Map.of("page", "/funds", "count", 104)
        ));
        example.put("userCount", 2);
        example.put("eventCount", 1133);
        example.put("activeUserIds", java.util.Arrays.asList(1));
        example.put("abnormalUserIds", new ArrayList<>());
        
        return ApiResponse.success(example);
    }

    // 提取字符串中的最大/最后一个JSON对象，支持多段JSON和补全右括号
    private String extractJson(String text) {
        if (text == null || text.trim().isEmpty()) {
            System.out.println("[AI原始返回] 空内容");
            return null;
        }
        
        System.out.println("[AI原始返回] " + text);
        
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
                    System.out.println("[AI提取] 代码块内容: " + candidate);
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
                System.out.println("[AI提取] JSON片段: " + candidate);
            }
        }
        
        // 3. 如果没有找到任何JSON片段，尝试从第一个 { 开始构建
        if (candidates.isEmpty()) {
            int start = text.indexOf('{');
            if (start >= 0) {
                String sub = text.substring(start);
                String completed = tryCompleteJson(sub);
                if (completed != null) {
                    candidates.add(completed);
                    System.out.println("[AI提取] 补全JSON: " + completed);
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
                System.out.println("[AI提取] 最终选择: " + candidate);
                return candidate;
            }
        }
        
        // 6. 如果都不行，尝试修复最后一个候选
        if (!candidates.isEmpty()) {
            String fixed = tryFixJson(candidates.get(0));
            if (fixed != null) {
                System.out.println("[AI提取] 修复后: " + fixed);
                return fixed;
            }
        }
        
        System.out.println("[AI提取] 未找到有效JSON");
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
} 