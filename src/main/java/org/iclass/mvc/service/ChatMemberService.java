package org.iclass.mvc.service;

import lombok.RequiredArgsConstructor;
import org.iclass.mvc.dao.ChatMemberMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMemberService {

    private final ChatMemberMapper dao;

    public int chatmemberjoin(String mem_id,int chat_roomnum){
        return dao.chatmemberjoin(mem_id,chat_roomnum);
    }

    public int chatmemberquit(int chat_roomnum){
        return dao.chatemeberquit(chat_roomnum);
    }
}
