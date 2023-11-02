package org.iclass.mvc.dao;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMemberMapper {

    int chatmemberjoin(String mem_id,int chat_roomnum);

    int chatemeberquit(int chat_roomnum);




}
