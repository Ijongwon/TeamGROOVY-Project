package org.iclass.mvc.service;

import lombok.RequiredArgsConstructor;
import org.iclass.mvc.dao.ChatMessageMapper;
import org.iclass.mvc.dto.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageMapper dao;

    public List<ChatMessage> chatlist(int roomnum){
        return dao.chatlist(roomnum);
    }
    public int chatinsert(ChatMessage vo){
        return dao.chatinsert(vo);
    }

    public List<String> chatmember(int roomnum){
        return dao.chatmember(roomnum);
    }
}
