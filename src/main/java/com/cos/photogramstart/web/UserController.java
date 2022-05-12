package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    
    @GetMapping("/user/{id}")
    public String profile(@PathVariable Integer id, Model model) {
        User userEntity = userService.회원프로필(id);
        model.addAttribute("user", userEntity);
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable Integer id,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        // System.out.println("세션 정보 확인 : " + principalDetails.getUser());
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // PrincipalDetails mPrincipalDetails = (PrincipalDetails) auth.getPrincipal();
        // System.out.println("세션 정보 확인 2 : " + mPrincipalDetails.getUser());
        return "user/update";
    }
}
