package com.example.seb45pre011.security;

//import com.example.seb45pre011.member.CustomOAuth2UserService;
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
        http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/h2/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .oauth2Login()
                .loginPage("/loginForm")
                .defaultSuccessUrl("/")
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
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
//        http
//
//                .cors().disable()
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/h2/**").permitAll()// H2 데이터베이스 콘솔 접근 허용
//                .anyRequest().permitAll()
//                .and()
//                .oauth2Login()
//                .loginPage("/loginForm")
//                .defaultSuccessUrl("/")
//                .userInfoEndpoint()
//                .userService(customOAuth2UserService).and().and().build();
//
//        http.headers().frameOptions().sameOrigin()
//
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);




    }
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new SimpleUrlLogoutSuccessHandler();
    }

    @Bean
    public LogoutHandler logoutHandler() {
        return new SecurityContextLogoutHandler();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        // OAuth 2.0 클라이언트 등록 정보를 설정하여 반환하는 코드를 작성
        // 예시: InMemoryClientRegistrationRepository 또는 다른 구현체 사용
        return new InMemoryClientRegistrationRepository(Arrays.asList(
                // 클라이언트 등록 정보들을 정의
        ));
    }

    @Override
    public void configure (WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/**");
    }
}