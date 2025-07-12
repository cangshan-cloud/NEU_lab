package com.neulab.fund.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户分群实体
 */
@Data
@Entity
@Table(name = "user_segment")
public class UserSegment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "segment_name", length = 64)
    private String segmentName;

    @Column(name = "rule", columnDefinition = "text")
    private String rule;

    @Column(name = "user_ids", columnDefinition = "text")
    private String userIds; // JSON数组字符串

    @Column(name = "created_at")
    private LocalDateTime createdAt;
} 