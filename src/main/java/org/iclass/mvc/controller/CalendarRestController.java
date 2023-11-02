package org.iclass.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.mvc.dto.ScheduleDto;
import org.iclass.mvc.dao.ScheduleMapper;
import org.iclass.mvc.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class CalendarRestController {

    private final UserService service;
    private final ScheduleMapper scheduleMapper;

    @GetMapping("/sch/{schdates}/{groupidx}")
    public List<ScheduleDto> calenderList(@PathVariable String schdates,
                                          @PathVariable String groupidx) {

        List<ScheduleDto> list = service.selectSchByDate(LocalDate.parse(schdates), groupidx);

        return list;
    }

    @GetMapping("/count/{schdates}/{groupidx}")
    public int calenderCount(@PathVariable String schdates,
                             @PathVariable String groupidx) {

        int countlist = service.isCount(LocalDate.parse(schdates), groupidx);

        return countlist;
    }

    @GetMapping("/Schedule/{schidx}")
    public ScheduleDto selectOne(@PathVariable long schidx){
        ScheduleDto dto = scheduleMapper.selectOne(schidx);

        log.info(">>>>>>> path variable schidx : {}",schidx);

        return dto;	// bookUser DTO 를 json 문자열로 변환시켜 전달합니다. (직렬화)
    }

    @DeleteMapping("/Schedule/{schidx}")
    public int delete(@PathVariable long schidx){

        int count = service.delete(schidx);

        log.info(">>>>>> path variable id : {}", schidx);

        return count;
    }

    @PatchMapping("/Schedule")
    public int changeMany(@RequestBody @Valid ScheduleDto dto) {
        int count = scheduleMapper.update(dto);

        return count;
    }


}
