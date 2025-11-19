package com.lblog.dto;

import com.lblog.domain.Message;

public class MessageDto {
    private Long id;

    private Long userId;

    private String title;

    private String content;

    private Integer isRead;

    private Long addTime;

    private Long editTime;

    //联表查询字段
    private String userName;

    public MessageDto(){
    }

    public static MessageDto messageDto(Message message,
                                        String userName){
        MessageDto messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setUserId(message.getUserId());
        messageDto.setTitle(message.getTitle());
        messageDto.setContent(message.getContent());
        messageDto.setIsRead(message.getIsRead());
        messageDto.setAddTime(message.getAddTime());
        messageDto.setEditTime(message.getEditTime());
        messageDto.setUserName(userName);

        return messageDto;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
