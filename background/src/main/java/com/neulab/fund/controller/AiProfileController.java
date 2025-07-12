package com.neulab.fund.controller;

import com.neulab.fund.kafka.AiProfileTaskProducer;
import com.neulab.fund.vo.ApiResponse;
import com.neulab.fund.entity.UserProfile;
import com.neulab.fund.repository.UserProfileRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/ai/profile")
@Tag(name = "AI画像分析", description = "异步AI用户画像分析接口")
public class AiProfileController {
    @Autowired
    private AiProfileTaskProducer aiProfileTaskProducer;
    @Autowired
    private UserProfileRepository userProfileRepository;

    @PostMapping("/async")
    @Operation(summary = "异步AI分析", description = "提交用户画像分析任务，立即返回分析中")
    public ApiResponse<String> asyncProfile(@RequestBody Map<String, Object> req) {
        System.out.println("【DEBUG】Controller 收到请求: " + req);
        try {
            Long userId = req.get("userId") instanceof Number ? ((Number) req.get("userId")).longValue() : null;
            String behaviorJson = req.get("behaviorJson") != null ? req.get("behaviorJson").toString() : null;
            System.out.println("【DEBUG】参数解析: userId=" + userId + ", behaviorJson=" + behaviorJson);
            if (userId == null || behaviorJson == null) {
                System.out.println("【DEBUG】参数校验失败");
                return ApiResponse.error("userId和behaviorJson不能为空");
            }
            System.out.println("【DEBUG】准备调用 Producer");
            aiProfileTaskProducer.sendProfileTask(userId, behaviorJson);
            System.out.println("【DEBUG】Producer 调用完成，准备 return");
            return ApiResponse.success("分析中");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("【DEBUG】异常 return: " + e.getMessage());
            return ApiResponse.error("AI分析任务提交失败");
        }
    }

    @GetMapping("/result")
    @Operation(summary = "查询AI分析结果", description = "通过userId查询AI画像分析结果")
    public ApiResponse<UserProfile> getProfileResult(@RequestParam Long userId) {
        return userProfileRepository.findByUserId(userId)
                .map(ApiResponse::success)
                .orElseGet(() -> ApiResponse.error("分析结果未生成"));
    }
} 