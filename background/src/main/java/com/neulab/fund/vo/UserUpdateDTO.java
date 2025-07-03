package com.neulab.fund.vo;

/**
 * 用户信息修改DTO
 */
public class UserUpdateDTO {
    private String realName;
    private String email;
    private String phone;
    // getter/setter
    public String getRealName() { return realName; }
    public void setRealName(String realName) { this.realName = realName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
} 