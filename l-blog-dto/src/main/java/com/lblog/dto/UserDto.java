package com.lblog.dto;

import com.lblog.domain.User;

public class UserDto {
    private Long id;

    private String name;

    private Integer status;

    private Integer registerType;

    private String registerIp;

    private Long addTime;

    private Long editTime;

    //联表查询字段
    private String visitIp;

    private Long visitTime;

    public UserDto(){
    }

    public static UserDto userDto(User user,
                                  String visitIp,
                                  Long visitTime){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setStatus(user.getStatus());
        userDto.setRegisterType(user.getRegisterType());
        userDto.setRegisterIp(user.getRegisterIp());
        userDto.setAddTime(user.getAddTime());
        userDto.setEditTime(user.getEditTime());
        userDto.setVisitIp(visitIp);
        userDto.setVisitTime(visitTime);

        return userDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRegisterType() {
        return registerType;
    }

    public void setRegisterType(Integer registerType) {
        this.registerType = registerType;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
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
}
