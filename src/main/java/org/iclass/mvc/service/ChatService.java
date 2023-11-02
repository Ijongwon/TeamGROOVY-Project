package org.iclass.mvc.service;

import lombok.RequiredArgsConstructor;
import org.iclass.mvc.dao.ChatRoomMapper;
import org.iclass.mvc.dto.ChatRoom;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomMapper dao;

    public List<ChatRoom> roomlist(String idx){
        return dao.roomlist(idx);
    }

    public List<ChatRoom> findByNum(int roomnum){
        return dao.findByNum(roomnum);
    }

    public int makechat(ChatRoom dto){
        return dao.makechat(dto);
    }

    public int roomNum(int roomnum){return dao.roomNum(roomnum);}

    public List<ChatRoom> selectByid(String userid,String groupidx){
        return dao.selectByid(userid,groupidx);
    }

    public int memberupcnt(int roomnum){
        return dao.memberupcnt(roomnum);
    }

    public int memberdowncnt(int roomnum){
        return dao.memberdowncnt(roomnum);
    }

}
