package org.iclass.mvc.dao;


import org.apache.ibatis.annotations.Mapper;
import org.iclass.mvc.dto.ChatRoom;

import java.util.List;

@Mapper
public interface ChatRoomMapper {

    int makechat(ChatRoom vo);

    List<ChatRoom> roomlist(String idx);

    List<ChatRoom> findByNum(int roomnum);

    int roomNum(int roomnum);

    List<ChatRoom> selectByid(String userid,String groupidx);

    int memberupcnt(int roomnum);

    int memberdowncnt(int roomnum);
}