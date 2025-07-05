package com.neulab.fund.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * 基金经理实体
 */
@Entity
@Table(name = "fund_manager")
public class FundManager {
    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** 经理代码 */
    @Column(name = "manager_code")
    private String managerCode;
    /** 经理姓名 */
    @Column(name = "manager_name")
    private String managerName;
    /** 性别 */
    @Column(name = "gender")
    private String gender;
    /** 出生日期 */
    @Column(name = "birth_date")
    private LocalDate birthDate;
    /** 学历 */
    @Column(name = "education")
    private String education;
    /** 从业年限 */
    @Column(name = "experience_years")
    private Integer experienceYears;
    /** 所属公司ID */
    @Column(name = "company_id")
    private Long companyId;
    /** 个人简介 */
    @Column(name = "introduction")
    private String introduction;
    /** 投资理念 */
    @Column(name = "investment_philosophy")
    private String investmentPhilosophy;
    /** 获奖情况 */
    @Column(name = "awards")
    private String awards;
    /** 状态 */
    @Column(name = "status")
    private String status;
    /** 创建时间 */
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    /** 更新时间 */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "name")
    private String name;
    @Column(name = "profile")
    private String profile;
    @Column(name = "user_id")
    private Long userId;

    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getManagerCode() { return managerCode; }
    public void setManagerCode(String managerCode) { this.managerCode = managerCode; }
    public String getManagerName() { return managerName; }
    public void setManagerName(String managerName) { this.managerName = managerName; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }
    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }
    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }
    public String getIntroduction() { return introduction; }
    public void setIntroduction(String introduction) { this.introduction = introduction; }
    public String getInvestmentPhilosophy() { return investmentPhilosophy; }
    public void setInvestmentPhilosophy(String investmentPhilosophy) { this.investmentPhilosophy = investmentPhilosophy; }
    public String getAwards() { return awards; }
    public void setAwards(String awards) { this.awards = awards; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
} 