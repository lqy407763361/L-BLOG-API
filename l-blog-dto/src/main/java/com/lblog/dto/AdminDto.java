package com.lblog.dto;

import com.lblog.domain.Admin;

public class AdminDto {
    private Long id;

    private Long groupId;

    private String account;

    private String name;

    private String description;

    private Integer status;

    private Long addTime;

    private Long editTime;

    //联表查询字段
    private String groupName;

    private Long prevLoginIp;

    private Long prevLoginTime;

    private Long lastLoginIp;

    private Long lastLoginTime;

    public AdminDto(){
    }

    public static AdminDto adminDto(Admin admin,
                                    String groupName,
                                    Long prevLoginIp,
                                    Long prevLoginTime,
                                    Long lastLoginIp,
                                    Long lastLoginTime){
        AdminDto adminDto = new AdminDto();
        adminDto.setId(admin.getId());
        adminDto.setGroupId(admin.getGroupId());
        adminDto.setAccount(admin.getAccount());
        adminDto.setName(admin.getName());
        adminDto.setDescription(admin.getDescription());
        adminDto.setStatus(admin.getStatus());
        adminDto.setAddTime(admin.getAddTime());
        adminDto.setEditTime(admin.getEditTime());
        adminDto.setGroupName(groupName);
        adminDto.setPrevLoginIp(prevLoginIp);
        adminDto.setPrevLoginTime(prevLoginTime);
        adminDto.setLastLoginIp(lastLoginIp);
        adminDto.setLastLoginTime(lastLoginTime);

        return adminDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public Long getEditTime() {
        return editTime;
    }

    public void setEditTime(Long editTime) {
        this.editTime = editTime;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getPrevLoginIp() {
        return prevLoginIp;
    }

    public void setPrevLoginIp(Long prevLoginIp) {
        this.prevLoginIp = prevLoginIp;
    }

    public Long getPrevLoginTime() {
        return prevLoginTime;
    }

    public void setPrevLoginTime(Long prevLoginTime) {
        this.prevLoginTime = prevLoginTime;
    }

    public Long getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(Long lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
