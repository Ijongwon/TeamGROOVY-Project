package org.iclass.mvc.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

    @Pattern(regexp = "^[a-z0-9]{4,20}$",message = "아이디는 영어 소문자와 숫자만 이용하여 4~20 글자로 하세요.")
    private String userid;  //유저 아이디

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$",
            message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    private String userpassword;    //유저 비밀번호

    @Pattern(regexp = "^[a-zA-Z가-힣]{2,10}$",message = "이름: 영대소문자와 한글만 가능 2글자 이상으로 하세요.")
    private String username;    //유저 이름

    private int userage;    //유저 나이

    private String usergender;  //유저 성별

    @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})?$"
            ,message = "이메일: 작성규칙이 올바르지 않습니다.")
    private String useremail;   //유저 이메일

    private String userphone;   //유저 번호
    private String userprofile; //유저 프로필
    private String userintro;//

    private MultipartFile pic;
}
