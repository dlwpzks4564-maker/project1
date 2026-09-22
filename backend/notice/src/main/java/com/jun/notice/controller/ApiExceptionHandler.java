package com.jun.notice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice  //RestControll 에 의해 자동응답
public class ApiExceptionHandler {
    //400 : HttpStatus.BAD_REQUEST : 입력값(파라미터)이 잘못됨. ->
    //404 : HttpStatus.UNAUTHORIZED :권한이 없는데 요청받은 경우(로그인이 안됨)
    //403 : HttpStatus.FORBIDDEN : 인증은 되었으나, 권한이없음(일반사용자 가 관리자페이지접근)
    //404 : HttpStatus.NOT_FOUND : 잘못된 URL 요청
    //405 : HttpStatus.METHOD_NOT_ALLOWED : 요청방식이 틀림(GET만 가능한데 POST요청)
    //500 : HttpStatus.INTERNAL_SERVER_ERROR : 핸들링 서버내부오류 (JAVA예외 발생)
    //500,503 다 같음 -> 500 으로 시작하는건 다 서버오류이다.
    //400오류
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)  //잘못된 요청,
    public Map<String, Object> handleValidation(MethodArgumentNotValidException e) {
        String massage = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(field -> field.getDefaultMessage())
                .orElse("입력값을 확인해주세요.");
        return error(HttpStatus.BAD_REQUEST.value(),massage); //400일때는 , "입력값을 확인해주세요."
    }

     //404  http://localhost:8081/api/notice/1 잘못된요청 404오류 / 옵셔널처리
    @ExceptionHandler(IllegalAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleIllegalArgument(IllegalAccessException e){
        return error(HttpStatus.NOT_FOUND.value(),e.getMessage()); //404 파라미터 잘못됐기때문에, "기본메세지"
    }

    //500 서버내부오류
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleException(Exception e) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(),"서버오류가 발생했습니다."); //500 "키 벨류 값이 넘어갔을때 서버오류가 발생했습니다."
    }
    //Java의 Map 은 -> JSON으로 내보내기 한다. (가능하면 선언해주는게 좋음 선택)
    public Map<String,Object> error(int status, String massage) {
      Map<String, Object> body = new LinkedHashMap<>();
      body.put("status",status);  //
      body.put("massage",massage);
      /*
       {
         "status" : 400,
         "massage" : "입력값을 확인해주세요."
       }
       */
        return body;
    }
}
