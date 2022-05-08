package com.cos.photogramstart.handler;

import java.util.Map;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {
    
    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e) {
        // CMRespDto와 Script와 비교
        // 1. 클라이언트에게 응답 할 때는 Script가 좋음
        // 2. AJAX 통신을 하거나 안드로이드와 통신을 하게 되면 CMRespDto가 좋다.
        // 즉, 개발자를 위한 응답에는 CMRespDto, 클라이언트를 위해서는 Script가 좋다.
        return Script.back(e.getErrorMap().toString());
    }
}
