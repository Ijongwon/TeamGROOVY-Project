package org.iclass.mvc.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.mvc.dao.UserMapper;
import org.iclass.mvc.dto.UserDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController2 {

    private final UserMapper userMapper;

    @PostMapping("/user")
    //요청에는 헤더와 바디가 있습니다. @RequestBody는 bookUser 가 요청의 바디라고 알려줍니다. -> 자바 객체로 자동변환
    // --> 클라이언트가 보낸 json 문자열을 자바 객체로 자동 변환
    public Map<String, Integer> save(@RequestBody @Valid UserDto userDto) {

        int count = userMapper.insert(userDto);
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("count", count);

        return resultMap;
    }
    @GetMapping("/user/email/{useremail}")  //이메일 중복검사
    public Map<String,Boolean> email(@PathVariable String useremail){
        int count = userMapper.isExistEmail(useremail);
        log.info(">>>>>>>> count : {} ",count);
        Map<String,Boolean> resultMap = new HashMap<>();
        resultMap.put("exist",(count==1));
        return  resultMap;
    }


    @GetMapping("/user/check/{userid}")
    public  Map<String,Boolean> check(@PathVariable String userid){
        int count = userMapper.isExist(userid);

        Map<String,Boolean> resultMap = new HashMap<>();
        resultMap.put("exist",(count==1));
        return  resultMap;		// map 은 key? : true

    }

}
