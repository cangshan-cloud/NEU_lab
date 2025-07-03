package com.neulab.fund.vo;

public class RoleChangeRequestDTO {
    private Long targetRoleId;
    private String reason;
    public Long getTargetRoleId() { return targetRoleId; }
    public void setTargetRoleId(Long targetRoleId) { this.targetRoleId = targetRoleId; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
} 