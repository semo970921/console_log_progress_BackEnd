package com.kh.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfigure {

  @Bean
  public PasswordEncoder passwordEncoder(){ // PasswordEncoder Bean으로 등록 완료
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityChain(HttpSecurity httpSecurity) throws Exception { // 필터체인 만들기
    return httpSecurity
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .build();

  }

}
