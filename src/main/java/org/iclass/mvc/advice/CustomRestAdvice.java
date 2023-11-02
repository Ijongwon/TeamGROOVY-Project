package org.iclass.mvc.advice;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice       //AOP 기능으로 구현된 오류처리 부가 기능을 구현한 Advice 클래스로 정의
@Log4j2
public class CustomRestAdvice {
    //Controller 에서 발생하는 예외를 json 형식으로 전달하기

    //Exception 처리하는 핸들러메소드 정의 : BindException만 처리하도록 함
    @ExceptionHandler(BindException.class)  //BindException이 발생하면 처리하는 메소드
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)  //응답코드 설정 417(서버오류)
    public ResponseEntity<Map<String, String>> handleBindException(BindException e) {
        log.error(e);
        Map<String, String> errorMap = new HashMap<>();
        if (e.hasErrors()) {
            BindingResult bindingResult = e.getBindingResult();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            });

        }
            return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)    //DataIntegrityViolationException이 발생하면 처리하는 메소드
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<Map<String,String>> handlerFKException(Exception e){
        log.error(e);
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("time", " " + System.currentTimeMillis());
        errorMap.put("message", "테이블 제약조건 위반입니다.");
        return ResponseEntity.badRequest().body(errorMap);

    }

}
