package com.cos.photogramstart.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    
    // 로그인 페이지로 이동
    @GetMapping("/auth/signin")
    public String signinForm() {
        return "/auth/signin";
    }

    // 회원가입 페이지로 이동
    @GetMapping("/auth/signup")
    public String signupForm() {
        return "/auth/signup";
    }

    // 회원가입 기능
    @PostMapping("/auth/signup")
    public String signup() {
        System.out.println("signup 실행됨");
        return "/auth/signin"; // 회원가입이 완료되면 로그인페이지로 이동할것이다.
    }

}
