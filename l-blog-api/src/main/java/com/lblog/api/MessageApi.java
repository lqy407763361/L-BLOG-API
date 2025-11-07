package com.lblog.api;

import com.lblog.api.auth.UserIdentityAuth;
import com.lblog.common.util.JsonResponseUtil;
import com.lblog.common.util.PageResultUtil;
import com.lblog.domain.Message;
import com.lblog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageApi {

    @Autowired
    private UserIdentityAuth userIdentityAuth;

    @Autowired
    private MessageService messageService;

    //发送消息
    @PostMapping("/sendMessage")
    public JsonResponseUtil<String> sendMessage(Message message){
        Long userId = userIdentityAuth.getCurrentUserId();
        messageService.sendMessage(message, userId);

        return JsonResponseUtil.success();
    }

    //编辑消息
    @PostMapping("/editMessage")
    public JsonResponseUtil<String> editMessage(Message message){
        messageService.editMessage(message);

        return JsonResponseUtil.success();
    }

    //删除消息
    @DeleteMapping("/deleteMessage")
    public JsonResponseUtil<String> deleteMessage(Long messageId){
        messageService.deleteMessage(messageId);

        return JsonResponseUtil.success();
    }

    //获取消息列表
    @GetMapping("/getMessageList")
    public JsonResponseUtil<PageResultUtil<Message>> getMessageList(@RequestParam(defaultValue = "1") Integer page,
                                                                    @RequestParam(defaultValue = "10") Integer size,
                                                                    Message message,
                                                                    String userName){
        PageResultUtil<Message> messageList = messageService.getMessageList(page, size, message, userName);

        return JsonResponseUtil.success(messageList);
    }

    //获取消息详情
    @GetMapping("/getMessageDetail")
    public JsonResponseUtil<Message> getMessageDetail(Long messageId){
        Message messageDetail = messageService.getMessageDetail(messageId);

        return JsonResponseUtil.success(messageDetail);
    }

    //获取消息数量
    @GetMapping("/getMessageTotal")
    public JsonResponseUtil<Integer> getMessageTotal(Message message, String userName){
        Integer messageTotal = messageService.getMessageTotal(message, userName);

        return JsonResponseUtil.success(messageTotal);
    }
}
