package org.iclass.mvc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.iclass.mvc.dto.ChatMessage;

import java.util.List;

@Mapper
public interface ChatMessageMapper {
    List<ChatMessage> chatlist(int roomnum);

    int chatinsert(ChatMessage vo);

    List<String> chatmember(int roomnum);

}
