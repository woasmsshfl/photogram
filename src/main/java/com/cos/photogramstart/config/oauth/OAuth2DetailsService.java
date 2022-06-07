package com.cos.photogramstart.config.oauth;

import java.util.Map;
import java.util.UUID;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("token : " + userRequest.getAccessToken().getTokenValue());
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oauth2User) {

        OAuth2UserInfo oAuth2UserInfo = null;

        if (userRequest.getClientRegistration().getClientName().equals("Google")) {
            oAuth2UserInfo = new GoogleInfo(oauth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getClientName().equals("Facebook")) {
            oAuth2UserInfo = new FacebookInfo(oauth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getClientName().equals("Naver")) {
            oAuth2UserInfo = new NaverInfo((Map)oauth2User.getAttributes().get("response"));
        } else if (userRequest.getClientRegistration().getClientName().equals("Kakao")) {
            oAuth2UserInfo = new KakaoInfo((Map)oauth2User.getAttributes());
        }

        System.out.println("============= Registration ID ================");
        System.out.println(userRequest.getClientRegistration().getRegistrationId());

        String username = oAuth2UserInfo.getUsername();
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());
        String email = oAuth2UserInfo.getEmail();
        String name = oAuth2UserInfo.getName();

        User userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {
            User user = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .role("ROLE_USER")
                    .build();
                
            userEntity = userRepository.save(user);



            return new PrincipalDetails(userEntity, oauth2User.getAttributes());
        } else {
            return new PrincipalDetails(userEntity, oauth2User.getAttributes());
        }
    }

}
