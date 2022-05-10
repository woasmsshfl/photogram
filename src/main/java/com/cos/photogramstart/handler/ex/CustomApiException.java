package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomApiException extends RuntimeException {

    // 객체를 구분하기 위해 시리얼넘버를 넣어주는것.
    // JVM을 위해 걸어주는 것이다.
    private static final long serialVersionUID = 1L;

    private Map<String, String> errorMap;

    public CustomApiException(String message) {
        super(message);
    }

    public CustomApiException(String message, Map<String, String> errorMap) {
        super(message);
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}
