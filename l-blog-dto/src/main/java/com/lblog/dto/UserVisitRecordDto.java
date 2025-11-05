package com.lblog.dto;

import com.lblog.domain.UserVisitRecord;

public class UserVisitRecordDto {
    private Long id;

    private Long userId;

    private String visitModule;

    private String visitIp;

    private Long visitTime;

    //联表查询字段
    private String userName;

    public UserVisitRecordDto(){
    }

    public static UserVisitRecordDto userVisitRecordDto(UserVisitRecord userVisitRecord,
                                                        String userName){
        UserVisitRecordDto userVisitRecordDto = new UserVisitRecordDto();
        userVisitRecordDto.setId(userVisitRecord.getId());
        userVisitRecordDto.setUserId(userVisitRecord.getUserId());
        userVisitRecordDto.setVisitModule(userVisitRecord.getVisitModule());
        userVisitRecordDto.setVisitIp(userVisitRecord.getVisitIp());
        userVisitRecordDto.setVisitTime(userVisitRecord.getVisitTime());
        userVisitRecordDto.setUserName(userName);

        return userVisitRecordDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getVisitModule() {
        return visitModule;
    }

    public void setVisitModule(String visitModule) {
        this.visitModule = visitModule;
    }

    public String getVisitIp() {
        return visitIp;
    }

    public void setVisitIp(String visitIp) {
        this.visitIp = visitIp;
    }

    public Long getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Long visitTime) {
        this.visitTime = visitTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
