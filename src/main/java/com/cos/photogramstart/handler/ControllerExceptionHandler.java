package com.cos.photogramstart.handler;

import java.util.Map;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.web.dto.CMRespDto;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {
    
    @ExceptionHandler(CustomValidationException.class)
    public CMRespDto<?> validationException(CustomValidationException e) {
        return new CMRespDto<>(-1, e.getMessage(), e.getErrorMap());
    }
}
