package com.neulab.fund.vo;

import lombok.Data;

/**
 * 用户画像VO
 */
@Data
public class UserProfileVO {
    private Long userId;
    private String profileText;
    private String tags;
} 