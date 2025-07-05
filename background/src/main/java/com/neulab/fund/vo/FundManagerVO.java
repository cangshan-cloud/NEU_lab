package com.neulab.fund.vo;

import java.time.LocalDate;

/**
 * 基金经理列表VO
 */
public class FundManagerVO {
    private Long id;
    private String managerCode;
    private String managerName;
    private String gender;
    private LocalDate birthDate;
    private String education;
    private Integer experienceYears;
    private Long companyId;
    private String introduction;
    private String investmentPhilosophy;
    private String awards;
    private String status;
    private String createdAt;
    private String updatedAt;
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
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}