package org.iclass.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.mvc.dao.GroupMapper;
import org.iclass.mvc.dto.GroupDto;
import org.iclass.mvc.service.GroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class IndexRestController
{
    private final GroupMapper groupMapper;
    private final GroupService groupService;

    @GetMapping("/all")
    public List<GroupDto> selectAll(){
        Map<String,String> map = new HashMap<>();
        List<GroupDto> list = groupMapper.selectAll(map);
        return list;
    }

    @GetMapping("/groups/{category}")
    public List<GroupDto> selectByCate(@PathVariable String category){
        Map<String,String> map = new HashMap<>();
        map.put("groupcate",category);
        List<GroupDto> list = groupService.selectBycate(map);
        return list;
    }



}
