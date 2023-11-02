package org.iclass.mvc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.iclass.mvc.dto.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface GroupMapper {
    int insert (GroupDto dto);
    int updatenowp(String groupidx);
    int downdatenowp(String groupidx);
    List<GroupDto> selectAll(Map<String,String> map);
    List<GroupDto> mychatgroupname(String userid);
    List<GroupDto> myHeart(String userid);
    List<GroupDto> myadmission(String userid);
    GroupMemberDto selectByuserid(String userid);
    GroupDto selectByidx(String groupidx);

    int groupupdate(GroupDto dto);

    String selectcate(String groupcate);
    List<GroupDto> selectBycate(Map<String,String> map);

    int memberInsert(GroupMemberDto dto);
    List<GroupAdDetailDto> admissionList(String groupidx);
    GroupAdDetailDto admissionMemList(String groupidx, String userid);
    int insertAdmission(GroupAdmissionDto dto);

    int updateAdmission(GroupAdmissionDto dto);

    int Admission(String groupidx, String userid);

    int isInGroup(String groupidx,String userid);
    int isHeartGroup(String groupidx,String userid);

    List<GroupMemberDto> memberList(String groupidx);

    int insertHeart(String groupidx,String userid);
    int deleteHeart(String groupidx,String userid);

    GroupMemberDetailDto groupmemberdetail(String groupidx, String userid);

    int memberDelete(String groupidx, String userid);
    int updateGroupMng(String groupidx, String userid);
}
