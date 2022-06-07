package com.cos.photogramstart.config;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity // 해당 파일로 시큐리티를 활성화 시키는 어노테이션
@Configuration // IoC컨테이너로 넣어주는 어노테이션
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2DetailsService oAuth2DetailsService;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO Auto-generated method stub
        // super.configure(http);

        http.csrf().disable() // CSRF토큰 검사를 비활성화 하겠다.
                .authorizeRequests() // 이 주소경로로 요청이 들어오면
                .antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**", "/api/**")
                .authenticated() // 인증이 필요하다.
                .anyRequest() // 그 외의 요청들은
                .permitAll() // 모두 허용한다.
                .and() // 그리고
                .formLogin() // 로그인(인증)이 필요한 요청이 들어오면
                .loginPage("/auth/signin") // 로그인페이지 auth/signin 으로 이동시키고(GET요청)
                .loginProcessingUrl("/auth/signin") // auth/signin 이라는 POST요청을 실행시킨다.
                .defaultSuccessUrl("/") // 인증이 정삭적으로 완료되면 / 로 이동한다.
                .and()
                .oauth2Login() // form로그인 외에 OAuth2 로그인도 인정하겠다.
                .userInfoEndpoint() // OAuth2 로그인 인증시 인증코드가 아닌 회원정보를 바로 받겠다.
                .userService(oAuth2DetailsService); // 회원정보를 담을 서비스 로직 설정영역.
    }   
}
