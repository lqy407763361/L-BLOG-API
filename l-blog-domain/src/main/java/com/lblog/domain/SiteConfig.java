package com.lblog.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "l_site_config")
public class SiteConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "meta_title")
    private String metaTitle;

    @Column(name = "meta_description")
    private String metaDescription;

    @Column(name = "meta_keywords")
    private String metaKeywords;

    @Column(name = "site_title")
    private String siteTitle;

    @Column(name = "site_list_limit")
    private Integer siteListLimit;

    @Column(name = "admin_list_limit")
    private Integer adminListLimit;

    @Column(name = "logo_image_url")
    private String logoImageUrl;

    @Column(name = "site_config")
    private String siteConfig;

    @Column(name = "system_maintenance")
    private Integer systemMaintenance;

    @Column(name = "site_login_max_number")
    private Integer siteLoginMaxNumber;

    @Column(name = "admin_login_max_number")
    private Integer adminLoginMaxNumber;

    @Column(name = "site_session_expire")
    private Long siteSessionExpire;

    @Column(name = "admin_session_expire")
    private Long adminSessionExpire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getSiteTitle() {
        return siteTitle;
    }

    public void setSiteTitle(String siteTitle) {
        this.siteTitle = siteTitle;
    }

    public Integer getSiteListLimit() {
        return siteListLimit;
    }

    public void setSiteListLimit(Integer siteListLimit) {
        this.siteListLimit = siteListLimit;
    }

    public Integer getAdminListLimit() {
        return adminListLimit;
    }

    public void setAdminListLimit(Integer adminListLimit) {
        this.adminListLimit = adminListLimit;
    }

    public String getLogoImageUrl() {
        return logoImageUrl;
    }

    public void setLogoImageUrl(String logoImageUrl) {
        this.logoImageUrl = logoImageUrl;
    }

    public String getSiteConfig() {
        return siteConfig;
    }

    public void setSiteConfig(String siteConfig) {
        this.siteConfig = siteConfig;
    }

    public Integer getSystemMaintenance() {
        return systemMaintenance;
    }

    public void setSystemMaintenance(Integer systemMaintenance) {
        this.systemMaintenance = systemMaintenance;
    }

    public Integer getSiteLoginMaxNumber() {
        return siteLoginMaxNumber;
    }

    public void setSiteLoginMaxNumber(Integer siteLoginMaxNumber) {
        this.siteLoginMaxNumber = siteLoginMaxNumber;
    }

    public Integer getAdminLoginMaxNumber() {
        return adminLoginMaxNumber;
    }

    public void setAdminLoginMaxNumber(Integer adminLoginMaxNumber) {
        this.adminLoginMaxNumber = adminLoginMaxNumber;
    }

    public Long getSiteSessionExpire() {
        return siteSessionExpire;
    }

    public void setSiteSessionExpire(Long siteSessionExpire) {
        this.siteSessionExpire = siteSessionExpire;
    }

    public Long getAdminSessionExpire() {
        return adminSessionExpire;
    }

    public void setAdminSessionExpire(Long adminSessionExpire) {
        this.adminSessionExpire = adminSessionExpire;
    }
}
