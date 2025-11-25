package com.lblog.dao;

import com.lblog.domain.Message;
import com.lblog.dto.MessageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageDao {
    Integer addMessage(Message Message);

    Integer editMessage(Message Message);

    Integer deleteMessage(@Param("id") List<Long> messageId);

    List<MessageDto> getMessageList(@Param("startNum") Integer startNum,
                                    @Param("size") Integer size,
                                    @Param("message") Message message,
                                    @Param("userName") String userName);

    Message getMessageDetail(@Param("id") Long messageId);

    MessageDto getMessageDetailDto(@Param("id") Long messageId);

    Integer getMessageTotal(@Param("message") Message message,
                            @Param("userName") String userName);
}
