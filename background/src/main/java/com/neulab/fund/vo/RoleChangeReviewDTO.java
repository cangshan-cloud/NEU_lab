package com.neulab.fund.vo;

public class RoleChangeReviewDTO {
    private String status; // APPROVED or REJECTED
    private String reviewComment;
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getReviewComment() { return reviewComment; }
    public void setReviewComment(String reviewComment) { this.reviewComment = reviewComment; }
} 