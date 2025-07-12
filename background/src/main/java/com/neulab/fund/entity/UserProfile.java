package com.neulab.fund.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户画像实体
 */
@Data
@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "profile_text", columnDefinition = "text")
    private String profileText;

    @Column(name = "tags", length = 255)
    private String tags;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
} 