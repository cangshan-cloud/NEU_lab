package com.neulab.fund.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 基金公司实体
 */
@Entity
@Table(name = "fund_company")
public class FundCompany {
    /** 主键ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** 公司代码 */
    @Column(name = "company_code")
    private String companyCode;
    /** 公司名称 */
    @Column(name = "company_name")
    private String companyName;
    /** 公司简称 */
    @Column(name = "company_short_name")
    private String companyShortName;
    /** 成立日期 */
    @Column(name = "establishment_date")
    private LocalDate establishmentDate;
    /** 注册资本 */
    @Column(name = "registered_capital")
    private BigDecimal registeredCapital;
    /** 法定代表人 */
    @Column(name = "legal_representative")
    private String legalRepresentative;
    /** 联系电话 */
    @Column(name = "contact_phone")
    private String contactPhone;
    /** 联系邮箱 */
    @Column(name = "contact_email")
    private String contactEmail;
    /** 公司网站 */
    @Column(name = "website")
    private String website;
    /** 状态 */
    @Column(name = "status")
    private String status;
    /** 公司描述 */
    @Column(name = "description")
    private String description;
    /** 创建时间 */
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    /** 更新时间 */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // getter/setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCompanyCode() { return companyCode; }
    public void setCompanyCode(String companyCode) { this.companyCode = companyCode; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getCompanyShortName() { return companyShortName; }
    public void setCompanyShortName(String companyShortName) { this.companyShortName = companyShortName; }
    public LocalDate getEstablishmentDate() { return establishmentDate; }
    public void setEstablishmentDate(LocalDate establishmentDate) { this.establishmentDate = establishmentDate; }
    public BigDecimal getRegisteredCapital() { return registeredCapital; }
    public void setRegisteredCapital(BigDecimal registeredCapital) { this.registeredCapital = registeredCapital; }
    public String getLegalRepresentative() { return legalRepresentative; }
    public void setLegalRepresentative(String legalRepresentative) { this.legalRepresentative = legalRepresentative; }
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
} 