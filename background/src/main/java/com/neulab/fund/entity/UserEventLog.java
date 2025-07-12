package com.neulab.fund.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户行为埋点日志实体
 * 存储前端所有用户行为埋点日志
 */
@Data
@Entity
@Table(name = "user_event_log")
public class UserEventLog {
    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 用户ID，未登录可为空 */
    @Column(name = "user_id")
    private Long userId;

    /** 事件类型（view/click/submit/error等） */
    @Column(name = "event_type", nullable = false, length = 32)
    private String eventType;

    /** 页面路径 */
    @Column(name = "page", nullable = false, length = 128)
    private String page;

    /** 操作按钮/元素ID，可为空 */
    @Column(name = "button_id", length = 64)
    private String buttonId;

    /** 事件参数，前端自定义，JSON字符串 */
    @Column(name = "event_params", columnDefinition = "json")
    private String eventParams;

    /** 异常信息，可为空 */
    @Column(name = "error_msg", length = 512)
    private String errorMsg;

    /** 用户IP */
    @Column(name = "ip_address", length = 64)
    private String ipAddress;

    /** 浏览器UA */
    @Column(name = "user_agent", length = 256)
    private String userAgent;

    /** 事件发生时间 */
    @Column(name = "created_at")
    private LocalDateTime createdAt;
} 