package org.iclass.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageResponseDTO {

    //페이지 목록의 범위를 계산하기
    //필요한 값
    private int totalPage;
    private int totalCount;

    private int startPage;
    private int endPage;
    private List<PostDTO> list;       //서비스에서 처리할 때 PageResponseDTO에 글목록을 포함시키면 리턴타입 정하기가 간단하고 view 에 전달도 한번에~

    public static PageResponseDTO of(PageRequestDTO dto, int totalCount, int pageSize) {
        //static 메소드이므로 static 변수만 사용할 수 있기 대문에 totalPage,startPage,endPage를 지역변수로 선언합니다.
        int totalPage = (int) Math.ceil((double) totalCount /dto.getSize()); //ceil 은 올림입니다.
        int startPage = (dto.getPage() - 1) / pageSize * pageSize + 1;         //페이지번호 리스트 pageSize 만큼. pageSize가 10이라면
        //현재 페이지에 대한 페이지 목록 시작 값 계산 : 현재페이지가 1~10 일 때는 startPage =1 , 11~20 일 때는 startPage =11

        int endPage = Math.min(startPage + pageSize - 1, totalPage);  //endPage > totalPage ? totalPage:endPage;   //totalPage 보다 큰값에 대한 제한.

        //계산된 값으로 객체 생성하여 리턴
        return PageResponseDTO.builder()
                .totalPage(totalPage)
                .totalCount(totalCount)
                .startPage(startPage)
                .endPage(endPage)
                .build();
    }
}