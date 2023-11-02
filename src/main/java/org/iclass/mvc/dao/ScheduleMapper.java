package org.iclass.mvc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.iclass.mvc.dto.ScheduleDto;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ScheduleMapper {

    List<ScheduleDto> selectByGroupIdx(String groupidx);
//    ScheduleDto selectBygroupIdx(String groupidx);
    List<ScheduleDto> selectSchByDate(LocalDate schdate, String groupidx);
//    ScheduleDto selectSchByDate(LocalDate schdate);
    int isCount(LocalDate schdate, String groupidx);
    ScheduleDto selectOne(long schidx);
    int schinsert(ScheduleDto dto);
    int update(ScheduleDto dto);
    int delete(long schidx);
}
