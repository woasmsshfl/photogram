package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {
    
    @GetMapping("/user/{id}")
    public String profile(@PathVariable Integer id) {
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable Integer id,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("세션 정보 확인 : " + principalDetails.getUser());
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // PrincipalDetails mPrincipalDetails = (PrincipalDetails) auth.getPrincipal();
        // System.out.println("세션 정보 확인 2 : " + mPrincipalDetails.getUser());
        return "user/update";
    }
}
