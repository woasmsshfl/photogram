package com.cos.photogramstart.config.auth;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 1. 스프링시큐리티가 password는 알아서 확인해주기 때문에 신경안써도 된다.
    // 2. 리턴이 잘 되면 자동으로 UserDetails 타입을 session으로 만들어준다.
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User userEntity = userRepository.findByUsername(username);

        if (userEntity == null) { // username을 찾지 못했다면
            return null; // null을 리턴하고
        } else { // username을 찾았다면
            return new PrincipalDetails(userEntity); // principalDetails를 session에 저장한다.
        }

    }
}
