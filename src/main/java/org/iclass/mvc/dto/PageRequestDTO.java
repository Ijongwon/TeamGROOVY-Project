package org.iclass.mvc.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;


import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class PageRequestDTO {

    // page, size는 start, end 계산에 필요한 값
    private int page = 1;   // 현재 페이지 번호
    private int size = 5;   //size 는 한 개 페이지 글 갯수

    // sql 쿼리에 필요한 값
    private int start;      // 페이지 시작 글번호 rownum
    private int end;      // 페이지 마지막 글번호 rownu

    //검색 파라미터
    // "twc"를 동적 쿼리로 전달하기 위해 배열로 변환하여 저장{"t","w","c"}
    private String[] types;

    // view 에서 "twc" 로 전달되는 값 저장
    private String type;

    private String keyword;

    @DateTimeFormat(pattern = "yyyy-MM-dd")   private LocalDate from;
    @DateTimeFormat(pattern = "yyyy-MM-dd")   private LocalDate to;

    // int page,int size,String[] types, String keyword,LocalDate from,LocalDate to 는
    // list.html 에서 검색 필드로 전달 해줄 파라미터들
    // list.html에서 검색 버튼을 누르면 /community/list getmapping 입니다.
    // 해당 핸들러 메소드에 인자로 PageRequestDto 선언합니다.
    //      ㄴ 핸들러 메소드는 PageRequestDTO 로 모든 파라미터를 받습니다.(생성자 + setter 동작)

    // 오라클에서만 필요합니다. mysql은 쉽게 할 수 있는 limit 라는 연산자가 있습니다.
    public void setDatas(){
        start =(page-1)* size+1;    //글목록 시작행번호(rownum)
        end = start + (size-1);

        // String "tc" 와 같이 view 에서 받은 파라미터 값은 배열로 변경
        if(type != null && !type.isEmpty() && !type.equals("a"))   // type="a" 를 전체로 할 예정
            types = type.split("");   // "tc" 를 new String[]{"t","c"} 로 변환합니다.
    }

    // 글읽기, 글 수정, 글 삭제 등의 페이지 번호와 검색 파라미터를 url 에 계속 데리고 다녀야 합니다.
    // 이를 위해서 문자열 생성 메소드 정의합니다.
    private String link;   // url 에 들어갈 파라미터 문자열

    // Getter 는 처리 코드가 필요하여 직접 작성합니다.
    public String getLink(){
        if(link == null){
            StringBuilder builder = new StringBuilder();
            builder.append("page=" + this.page);
            if(type != null && this.type.length() > 0 && this.keyword != null){
                builder.append("&type=" + type);
                try {
                    builder.append("&keyword=" + URLEncoder.encode(this.keyword, "UTF-8"));
                    // 키워드는 한글 등 다국어 문자일 경우 인코딩이 필요합니다.
                } catch (UnsupportedEncodingException e){

                }
            }

            if (this.from != null && this.to != null){   // 아직 UI 에는 미구현한 상태입니다.
                builder.append("&from=" + this.from);
                builder.append("&to=" + this.to);
            }

            this.link = builder.toString();
        }

        return this.link;
        // 최종 link 는 예시) page=3&type=tc&keyword=hi&from=2023-03-01&to=2023-03-31
    }
}