package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.cos.photogramstart.domain.user.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User{
    
    private static final long serialVersionUID = 1L;

    private User user;
    private Map<String, Object> attributes;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // OAuth2 유저 구분을 위한 오버로딩
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
    }

    // 권한을 가져오는 메서드( user의 role) =====================================
    // 유저마다 권한이 1개가 아닐수도 있기 때문에 컬렉션 타입으로 받아야 한다.
    @Override 
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // GrantedAuthority 타입으로 받아주기.
        Collection<GrantedAuthority> collector = new ArrayList<>();
        // 비어있는 권한을 부여해주기
        collector.add(() -> {    
                return user.getRole();        
        });

        return collector;
    }

    // user 정보를 가져오는 Getter ===========================================
    @Override // user의 password를 가져오는 메서드
    public String getPassword() {
        return user.getPassword();
    }

    @Override // user의 username을 가져오는 메서드
    public String getUsername() {
        return user.getUsername();
    }

    // return이 true일 때, 정상적으로 로그인 로직이 실행 됨==========================
    @Override  // 계정이 만료되지 않았는가?
    public boolean isAccountNonExpired() {
        return true; // 응, 만료되지 않았어.
    }

    @Override // 계정이 잠기지 않았는가?
    public boolean isAccountNonLocked() {
        return true; // 응, 계정이 잠기지 않았어.
    }

    @Override // 비밀번호 변경한지 오래되지 않았는가?
    public boolean isCredentialsNonExpired() {
        return true; // 응, 변경한지 오래되지 않았어.
    }

    @Override // 계정이 활성화 되어있는가?
    public boolean isEnabled() {
        return true; // 응, 계정 활성화가 되어있어.
    }


    // OAuth2User 타입을 implement 함. 
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}
