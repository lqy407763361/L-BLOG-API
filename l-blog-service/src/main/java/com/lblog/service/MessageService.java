package com.lblog.service;

import com.lblog.common.exception.ReturnException;
import com.lblog.common.util.PageResultUtil;
import com.lblog.dao.MessageDao;
import com.lblog.domain.Message;
import com.lblog.dto.MessageDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    //发送消息
    @Transactional
    public void sendMessage(Message message, Long userId){
        //判断名称是否合法
        String title = message.getTitle().trim();
        if(StringUtils.isBlank(title)){
            throw new ReturnException("标题不能为空！");
        }

        //判断内容是否合法
        String content = message.getContent().trim();
        if(StringUtils.isBlank(content)){
            throw new ReturnException("内容不能为空！");
        }

        Integer isRead = 0;
        Long addTime = Instant.now().getEpochSecond();
        message.setUserId(userId);
        message.setTitle(title);
        message.setContent(content);
        message.setIsRead(isRead);
        message.setAddTime(addTime);
        messageDao.addMessage(message);
    }

    //编辑消息
    @Transactional
    public void editMessage(Message message){
        //判断消息是否存在
        Long messageId = message.getId();
        Message messageDetail = this.getMessageDetail(messageId);
        if(messageDetail == null){
            throw new ReturnException("消息已是已读状态！");
        }

        Integer isRead = 1;
        Long editTime = Instant.now().getEpochSecond();
        message.setId(messageId);
        message.setIsRead(isRead);
        message.setEditTime(editTime);
        messageDao.editMessage(message);
    }

    //删除消息
    @Transactional
    public void deleteMessage(Map<String, List<Long>> messageId){
        //判断消息ID
        if((messageId == null) || messageId.isEmpty()){
            throw new ReturnException("消息ID不能为空！");
        }

        List<Long> messageIdList = messageId.get("id");
        messageDao.deleteMessage(messageIdList);
    }

    //获取消息列表
    public PageResultUtil<MessageDto> getMessageList(Integer page, Integer size, Message message, String userName){
        //起始位置
        Integer startNum = (page-1) * size;
        //获取总数
        Integer total = messageDao.getMessageTotal(message, userName);
        //查询列表
        List<MessageDto> messageList = messageDao.getMessageList(startNum, size, message, userName);

        return new PageResultUtil<>(page, size, total, messageList);
    }

    /**
     * 获取消息详情
     * 用于内部查询，编辑
     * */
    @Transactional
    public Message getMessageDetail(Long messageId){
        //判断消息ID
        if((messageId == null) || (messageId == 0)){
            throw new ReturnException("消息ID不能为空！");
        }

        return messageDao.getMessageDetail(messageId);
    }

    /**
     * 获取消息详情
     * 重新组装展示字段
     * */
    @Transactional
    public MessageDto getMessageDetailDto(Long messageId){
        //判断消息ID
        if((messageId == null) || (messageId == 0)){
            throw new ReturnException("消息ID不能为空！");
        }

        return messageDao.getMessageDetailDto(messageId);
    }

    //获取消息数量
    public Integer getMessageTotal(Message message, String userName){
        return messageDao.getMessageTotal(message, userName);
    }
}
