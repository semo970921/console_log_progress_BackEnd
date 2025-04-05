package com.kh.spring.auth.service;

import com.kh.spring.auth.model.dto.LoginDTO;
import com.kh.spring.auth.model.vo.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;


import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

  private final AuthenticationManager authenticationManager;

  // 로그인
  @Override
  public Map<String, String> login(@Valid LoginDTO member){

    Authentication authenticaion = null;
    try{
      authenticaion = authenticationManager.authenticate
              (new UsernamePasswordAuthenticationToken(
                      member.getMemberEmail(),
                      member.getMemberPassword()
              ));
    } catch(BadCredentialsException e){
      throw new BadCredentialsException("이메일 또는 비밀번호를 잘못 입력하셨습니다.");
    }

    CustomUserDetails user = (CustomUserDetails) authenticaion.getPrincipal();

    log.info("로그인 성공!");
    log.info("인증에 성공한 사용자의 정보 : {}", user);

    Map<String, String> loginResponse = new HashMap<>();
//    loginResponse.put("message", "로그인 성공~~👻");
//    loginResponse.put("memberEmail", user.getUserEmail());
//    loginResponse.put("memberId", user.getUsername());
//    loginResponse.put("memberName", user.getUserName());

    loginResponse.put("message", "로그인 성공~~👻");
    loginResponse.put("memberName", user.getMemberName());
    loginResponse.put("memberEmail", user.getUsername());
    loginResponse.put("memberPassword", user.getPassword());



    return loginResponse;

  }

}
