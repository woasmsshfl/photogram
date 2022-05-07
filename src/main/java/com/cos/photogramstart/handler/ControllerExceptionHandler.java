package com.cos.photogramstart.handler;

import java.util.Map;

import com.cos.photogramstart.handler.ex.CustomValidationException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {
    
    @ExceptionHandler(CustomValidationException.class)
    public Map<String, String> validationException(CustomValidationException e) {
        return e.getErrorMap();
    }
}
