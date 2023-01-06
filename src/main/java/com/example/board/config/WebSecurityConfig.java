package com.example.board.config;


import com.example.board.jwt.JwtAuthFilter;
import com.example.board.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig {

//    private final UserDetailsServiceImpl userDetailsService;

    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2-console 사용 및 resources 접근 허용 설정
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        //기본 설정인 session방식은 사용하지 않고 jwt방식을 사용하기 위한 설정.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //시큐리티 업그레이드 되면서 바뀜 리퀘스트 매처
        http.authorizeHttpRequests().requestMatchers("/api/user/**").permitAll()
                //antMatchers 설정한 리소스의 접근을 인증절차 없이 허용한다는 의미.  permitall 로그인 하지 않고 모두 권한을 가짐.

//                .requestMatchers().hasAnyRole("ADMIN")
                //어드민 권한 만들기

                .anyRequest().authenticated()
                // 그 외 모든 요청은 인증된 사용자만 접근 가능

                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        //jwt 인증 인가를 사용하기 위한 설정
//
//        // Custom Filter 등록하기
//        http.addFilterBefore(new CustomSecurityFilter(userDetailsService, passwordEncoder()), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}