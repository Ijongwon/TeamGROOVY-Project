package org.iclass.mvc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.iclass.mvc.dto.UserDto;

import java.util.Map;

@Mapper
public interface UserMapper {
    int insert(UserDto dto);
    UserDto selectByid(String userid);
    UserDto login(Map<String,String> map);//로그인
    String selectPic(String userid);

    int isExist(String userid); //아이디 중복검사
    int isExistEmail(String useremail);//이메일중복검사

    int update(UserDto dto);

}
