package org.iclass.mvc.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.iclass.mvc.dao.ParticipantsMapper;
import org.iclass.mvc.dao.ScheduleMapper;
import org.iclass.mvc.dao.UserMapper;
import org.iclass.mvc.dto.ScheduleDto;
import org.iclass.mvc.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)	//생성자 접근 권한
public class UserService {
    private final UserMapper dao;
    private final ScheduleMapper schdao;
    private final ParticipantsMapper ptpdao;

    public int insert(UserDto dto) {


        String path ="D:\\iclass0419\\upload";
        StringBuilder filenames = new StringBuilder();
        MultipartFile f = dto.getPic();
        if(f.getSize()!=0){
            String ofilename = f.getOriginalFilename();
            String postfix = ofilename.substring(ofilename.lastIndexOf("."));
            StringBuilder newfile = new StringBuilder("user_")
                    .append(UUID.randomUUID().toString().substring(0,8)).append(postfix);

            File file = new File(path+"\\"+newfile);
            try {
                f.transferTo(file);
            } catch (IOException e) {

            }

            dto.setUserprofile(newfile.toString());
        } else {
            dto.setUserprofile("defaultuserpro.png");
        }

        return dao.insert(dto);
    }








    public UserDto login(Map<String,String> map) {return dao.login(map);}

    public UserDto selectByid(String userid){
        return dao.selectByid(userid);
    }

    public String selectPic(String userid) {
        return dao.selectPic(userid);
    }
    public int schinsert(ScheduleDto dto) {

        return schdao.schinsert(dto);
    }

    public List<ScheduleDto> selectSchByDate(LocalDate schdate, String groupidx){

        List<ScheduleDto> list = schdao.selectSchByDate(schdate, groupidx);

        return list;
    }

    public int isCount(LocalDate schdate, String groupidx) {

        return schdao.isCount(schdate, groupidx);
    }

    public List<ScheduleDto> selectByGroupIdx(String groupidx) {

        List<ScheduleDto> list = schdao.selectByGroupIdx(groupidx); //fK로 조회

        return list;
    }

    public int delete(long schidx) {

        return schdao.delete(schidx);
    }

    public int update(UserDto dto){ String path ="D:\\iclass0419\\upload";
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

            dto.setUserprofile(newfile.toString());
        } else {
            dto.setUserprofile(dao.selectByid(dto.getUserid()).getUserprofile());

        } return dao.update(dto);
    }

}

