package com.example.seb45pre011.security;

//import com.example.seb45pre011.member.CustomOAuth2UserService;
import com.example.seb45pre011.member.TokenBlackList;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;


@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private  CustomOAuth2UserService customOAuth2UserService;
    private  JwtProvider jwtProvider;
    private TokenBlackList tokenBlackList;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService,JwtProvider jwtProvider){
        this.customOAuth2UserService = customOAuth2UserService;
        this.jwtProvider = jwtProvider;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /*
        protected void configure(HttpSecurity http) throws Exception {
    http
        .cors().disable() // CORS 설정
        .csrf().disable() // CSRF 설정
        .authorizeRequests
            .antMatchers("//**").hasRole("ADMIN")
            .antMatchers("/admin/**").hasRole("ADMIN") // ADMIN 역할을 가진 사용자만 접근 허용
            .antMatchers("/user/**").hasAnyRole("ADMIN", "USER") // ADMIN 또는 USER 역할을 가진 사용자만 접근 허용
            .anyRequest().authenticated() // 나머지 요청은 인증 필요
        .and()
        .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessHandler(logoutSuccessHandler())
            .addLogoutHandler(logoutHandler())
            .permitAll()
        .and()
        .headers().frameOptions().sameOrigin() // H2 콘솔을 사용할 경우, X-Frame-Options 설정
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 관리 정책 설정
        .and()
        .addFilterBefore(new JwtAuthenticationFilter(jwtProvider, tokenBlackList), UsernamePasswordAuthenticationFilter.class);
}
         */
        http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/h2/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessHandler(logoutSuccessHandler())
                .addLogoutHandler(logoutHandler())
                .permitAll();

        http.headers().frameOptions().sameOrigin()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider,tokenBlackList), UsernamePasswordAuthenticationFilter.class);

    }
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new SimpleUrlLogoutSuccessHandler();
    }

    @Bean
    public LogoutHandler logoutHandler() {
        return new SecurityContextLogoutHandler();
    }



    @Override
    public void configure (WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/**");
    }
}