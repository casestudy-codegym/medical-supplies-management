package com.medicalsuppliesmanagement.config;

import com.medicalsuppliesmanagement.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(auth -> auth
                // Cho phép truy cập không cần xác thực
                .requestMatchers("/", "/auth/**", "/css/**", "/js/**", "/images/**", "/error/**").permitAll()
                // Yêu cầu ADMIN role cho các trang quản trị
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // Yêu cầu đăng nhập cho các trang khác
                .anyRequest().authenticated()
            );
            http.formLogin(form -> form
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login-process")
                .defaultSuccessUrl("/auth/login-success", true)
                .failureUrl("/auth/login?error=true")
                .usernameParameter("username")//trùng với tên trong form đăng nhập
                .passwordParameter("password")// trung với tên trong form đăng nhập
                .permitAll()
            );
            http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                .logoutSuccessUrl("/auth/login?logout=true")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );
            http.rememberMe(remember -> remember
                .key("uniqueAndSecretKey")
                .tokenValiditySeconds(86400) // 1 ngày
            );
        
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
    }
} 