package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {
    
    // JavaScript 응답하는 핸들러
    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e) {
        // CMRespDto와 Script와 비교
        // 1. 클라이언트에게 응답 할 때는 Script가 좋음
        // 2. AJAX 통신을 하거나 안드로이드와 통신을 하게 되면 CMRespDto가 좋다.
        // 즉, 개발자를 위한 응답에는 CMRespDto, 클라이언트를 위해서는 Script가 좋다.
        if (e.getErrorMap() == null) {
            return Script.back(e.getMessage());
        } else {
            return Script.back(e.getErrorMap().toString());
        }
    }

    // profile 페이지에서 사용될 핸들러
    @ExceptionHandler(CustomException.class)
    public String exception(CustomException e) {
        return Script.back(e.getMessage());
    }

    // CMRespDto 오브젝트 + HttpStatus 상태코드를 응답하는 핸들러
    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
        return new ResponseEntity<>(
                new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()),
                HttpStatus.BAD_REQUEST);
    }
    
    // CMRespDto 오브젝트 + HttpStatus 상태코드를 응답하는 핸들러
    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e) {
        return new ResponseEntity<>(
                new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()),
                HttpStatus.BAD_REQUEST);
    }
}
