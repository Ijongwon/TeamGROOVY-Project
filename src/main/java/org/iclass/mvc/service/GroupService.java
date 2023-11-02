package org.iclass.mvc.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.iclass.mvc.dao.GroupMapper;
import org.iclass.mvc.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupService {

    private final GroupMapper dao;

    public int insert(GroupDto dto){

        /* 카테고리로 idx 설정 */
        String cate = dto.getGroupcate();
        switch (cate) {
            case "문화·예술" : cate = "AT"; break;
            case "운동" : cate = "EX"; break;
            case "음식" : cate = "FD"; break;
            case "취미" : cate = "HB"; break;
            case "여행" : cate = "TV"; break;
            case "자기계발" : cate = "SD"; break;
            case "동네·친목" : cate = "SC"; break;
            case "재테크" : cate = "FT"; break;
            case "외국어" : cate = "FN"; break;
        }
        String cates = dao.selectcate(cate);
        if(cates == null) {
            cates = cate+"0001";
        } else {
            String num = cates.substring(2);
            int tonum = Integer.parseInt(num)+1;
            if (tonum < 10) {
                cates = cate+String.format("000%d", tonum);
            } else if (tonum < 100) {
                cates = cate+String.format("00%d", tonum);
            } else {
                cates = cate+String.format("0%d", tonum);
            }
        }
        dto.setGroupidx(cates);

        /* 파일 업로드 */
        String path ="D:\\iclass0419\\upload";
        StringBuilder filenames = new StringBuilder();
        MultipartFile f = dto.getPic();
        if(f.getSize()!=0){
            String ofilename = f.getOriginalFilename();
            String postfix = ofilename.substring(ofilename.lastIndexOf("."));
            StringBuilder newfile = new StringBuilder("group_")
                    .append(UUID.randomUUID().toString().substring(0,8)).append(postfix);

            File file = new File(path+"\\"+newfile);
            try {
                f.transferTo(file);
            } catch (IOException e) {

            }

            dto.setGrouppic(newfile.toString());
        } else {
            String defaultpic = "default"+cate+".jpg";
            dto.setGrouppic(defaultpic);
        }


        return dao.insert(dto);
    }

    public int groupupdate(GroupDto dto) {

        /* 파일 업로드 */
        String path ="D:\\iclass0419\\upload";
        StringBuilder filenames = new StringBuilder();
        MultipartFile f = dto.getPic();

        if(f.getSize()!=0){
            String ofilename = f.getOriginalFilename();
            String postfix = ofilename.substring(ofilename.lastIndexOf("."));
            StringBuilder newfile = new StringBuilder("group_")
                    .append(UUID.randomUUID().toString().substring(0,8)).append(postfix);

            File file = new File(path+"\\"+newfile);
            try {
                f.transferTo(file);
            } catch (IOException e) {

            }

            dto.setGrouppic(newfile.toString());
        } else {
            dto.setGrouppic(dao.selectByidx(dto.getGroupidx()).getGrouppic());
        }


        return dao.groupupdate(dto);
    }

    public List<GroupDto> selectBycate(Map<String,String> map){
        String cate = null;
        switch (map.get("groupcate")) {
            case "AT" : cate = "문화·예술"; break;
            case "EX" : cate = "운동"; break;
            case "FD" : cate = "음식"; break;
            case "HB" : cate = "취미"; break;
            case "TV" : cate = "여행"; break;
            case "SD" : cate = "자기계발"; break;
            case "SC" : cate = "동네·친목"; break;
            case "FT" : cate = "재테크"; break;
            case "FN" : cate = "외국어"; break;
        }
        Map<String,String> catemap = new HashMap<>();
        catemap.put("groupcate", cate);
        catemap.put("keyword",map.get("keyword"));

        List<GroupDto> list = dao.selectBycate(catemap);
        return list;
    }

    public int updatenowp(String groupidx) {
        return dao.updatenowp(groupidx);
    }
    public int downdatenowp(String groupidx) {
        return dao.downdatenowp(groupidx);
    }

    public int memberDelete(String groupidx, String userid) {
        return dao.memberDelete(groupidx,userid);
    }

    public GroupDto selectByidx(String groupidx){
        return dao.selectByidx(groupidx);
    }

    public int isInGroup(String groupidx,String userid) {
        return dao.isInGroup(groupidx,userid);
    }

    public int Admission(String groupidx, String userid) {
        return  dao.Admission(groupidx,userid);
    }

    public int memberInsert(GroupMemberDto dto) {
        return dao.memberInsert(dto);
    }

    public int insertAdmission(GroupAdmissionDto dto) {
        return dao.insertAdmission(dto);
    }
    public int updateAdmission(GroupAdmissionDto dto){
        return dao.updateAdmission(dto);
    };
    public int isHeartGroup(String groupidx,String userid) {
        return dao.isHeartGroup(groupidx,userid);
    }

    public List<GroupMemberDto> memberList(String groupidx) {
        return  dao.memberList(groupidx);
    }
    public List<GroupAdDetailDto> admissionList(String groupidx) {
        return  dao.admissionList(groupidx);
    }
    public int insertHeart(String groupidx,String userid) {
        return dao.insertHeart(groupidx,userid);
    }
    public int deleteHeart(String groupidx,String userid) {
        return dao.deleteHeart(groupidx,userid);
    }
    public GroupMemberDetailDto groupmemberdetail(String groupidx, String userid){
        return  dao.groupmemberdetail(groupidx,userid);
    }

    public GroupAdDetailDto admissionMemList(String groupidx,String userid) {
        return dao.admissionMemList(groupidx,userid);
    }

    public int updateGroupMng(String groupidx, String userid) {
        return dao.updateGroupMng(groupidx,userid);
    }

    public GroupMemberDto selectByuserid(String userid){
        return dao.selectByuserid(userid);
    }

    public List<GroupDto> mychatgroupname(String userid){
        return dao.mychatgroupname(userid);
    }
    public List<GroupDto> myadmission(String userid){
        return dao.myadmission(userid);
    }
    public List<GroupDto> myHeart(String userid){
        return dao.myHeart(userid);
    }


}
