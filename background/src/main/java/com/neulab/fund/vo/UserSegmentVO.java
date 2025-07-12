package com.neulab.fund.vo;

import lombok.Data;
import java.util.List;

/**
 * 用户分群VO
 */
@Data
public class UserSegmentVO {
    private String segmentName;
    private String rule;
    private List<Long> userIds;
} 