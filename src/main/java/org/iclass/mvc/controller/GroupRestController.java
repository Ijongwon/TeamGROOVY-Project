package org.iclass.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.mvc.dao.GroupMapper;
import org.iclass.mvc.dto.*;
import org.iclass.mvc.service.GroupService;
import org.iclass.mvc.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/group")
public class GroupRestController {

    private final GroupService service;
    private final UserService uservice;
    private final GroupMapper groupMapper;
    @GetMapping("/hearts/{groupidx}")
    public int isHeartGroup(@PathVariable String groupidx, HttpSession session){
        String id = (String)session.getAttribute("userid");
        if (id == null) {
            id = "null";
        }
        return service.isHeartGroup(groupidx,id);
    }

    @GetMapping("/heartsonoff/{groupidx}")
    public int heartsonoff(@PathVariable String groupidx, HttpSession session){
        String id = (String)session.getAttribute("userid");
        if (id == null) {
            id = "null";
        }
        int status = service.isHeartGroup(groupidx,id);
        if(status == 1) {
            service.deleteHeart(groupidx,id);
            return 0;
        } else {
            service.insertHeart(groupidx,id);
            return 1;
        }
    }

    @GetMapping("/members/{groupidx}")
    public List<GroupMemberDto> members(@PathVariable String groupidx){
        List<GroupMemberDto> list = service.memberList(groupidx);
        return list;
    }
    @GetMapping("/admembers/{groupidx}")
    public List<GroupAdDetailDto> admembers(@PathVariable String groupidx){
        List<GroupAdDetailDto> list = service.admissionList(groupidx);
        return list;
    }

    @GetMapping("/memberdetail/{groupidx}/{userid}")
    public GroupMemberDetailDto memdetail(@PathVariable String groupidx, @PathVariable String userid) {
        GroupMemberDetailDto dto = service.groupmemberdetail(groupidx,userid);
        return dto;
    }
    @GetMapping("/admember/{groupidx}/{userid}")
    public GroupAdDetailDto addetail(@PathVariable String groupidx, @PathVariable String userid) {
        GroupAdDetailDto dto = service.admissionMemList(groupidx,userid);
        return dto;
    }

    @GetMapping("/admissiontof/{groupidx}/{userid}/{tof}")
    public List<GroupAdDetailDto> admissiontof(@PathVariable String groupidx,
                             @PathVariable String userid,
                             @PathVariable String tof){
        GroupAdmissionDto gdto = GroupAdmissionDto.builder()
                .groupidx(groupidx)
                .userid(userid)
                .build();
        log.info("tof : {}",tof);
        if(tof.equals("수락")) {
            gdto.setAdstatus("수락");

            GroupMemberDto gmdto = GroupMemberDto.builder()
                    .groupidx(groupidx)
                    .userid(userid)
                    .userprofile(uservice.selectByid(userid).getUserprofile())
                    .build();
            service.updateAdmission(gdto);
            service.memberInsert(gmdto);
            service.updatenowp(groupidx);
        } else {
            gdto.setAdstatus("거절");
            service.updateAdmission(gdto);
        }

        return service.admissionList(groupidx);
    }

    @GetMapping("/deleteMember/{groupidx}/{userid}")
    public void memberDelete(@PathVariable String groupidx, @PathVariable String userid) {
        service.memberDelete(groupidx,userid);
        service.downdatenowp(groupidx);

    }

    @GetMapping("/updateGroupMng/{groupidx}/{userid}")
    public void updateGroupMng(@PathVariable String groupidx, @PathVariable String userid){
        service.updateGroupMng(groupidx,userid);
    }

    @GetMapping("/all")
    public List<GroupDto> selectAll(){
        Map<String,String> map = new HashMap<>();
        List<GroupDto> list = groupMapper.selectAll(map);
        return list;
    }
    @GetMapping("/all/{keyword}")
    public List<GroupDto> selectAllSearch(@PathVariable String keyword){
        Map<String,String> map = new HashMap<>();
        map.put("keyword",keyword);
        List<GroupDto> list = groupMapper.selectAll(map);
        return list;
    }

    @GetMapping("/groups/{category}/{keyword}")
    public List<GroupDto> selectByCate(@PathVariable String category,@PathVariable String keyword){
        Map<String,String> map = new HashMap<>();
        map.put("groupcate",category);
        if (keyword.equals("null")) {
            map.put("keyword",null);
        } else {
            map.put("keyword",keyword);
        }
        log.info(">>>>>>>>>>>>>keyword : {}",map.get("keyword"));
        List<GroupDto> list = service.selectBycate(map);
        return list;
    }

    @GetMapping("/groupcate/{groupidx}")
    public String groupcate(@PathVariable String groupidx) {
        String cate = groupMapper.selectByidx(groupidx).getGroupcate();
        String cates = "";
        switch (cate) {
            case "문화·예술" : cates = "at"; break;
            case "운동" : cates = "ex"; break;
            case "음식" : cates = "fd"; break;
            case "취미" : cates = "hb"; break;
            case "여행" : cates = "tv"; break;
            case "자기계발" : cates = "sd"; break;
            case "동네·친목" : cates = "sc"; break;
            case "재테크" : cates = "ft"; break;
            case "외국어" : cates = "fn"; break;
        }
        log.info(cates);
        return cates;
    }

    @GetMapping("/groupdetail/{groupidx}")
    public String groupdetail(@PathVariable String groupidx) {
        String detail = groupMapper.selectByidx(groupidx).getGroupdetail();
        log.info(detail);
        return detail;
    }

    @GetMapping("/groupstatus/{groupidx}")
    public String groupstatus(@PathVariable String groupidx) {
        String status = groupMapper.selectByidx(groupidx).getGroupstatus();
        String statuss = "";
        if(status.equals("선착순")){
            statuss = "fi";
        } else {
            statuss = "ad";
        }
        log.info(statuss);
        return statuss;
    }
}
