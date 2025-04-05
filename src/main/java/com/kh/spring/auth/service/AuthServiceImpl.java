package com.kh.spring.auth.service;

import com.kh.spring.auth.model.dto.LoginDTO;
import com.kh.spring.auth.model.vo.CustomUserDetails;
import com.kh.spring.token.model.service.TokenService;
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
  private final TokenService tokenService;

  // 로그인
  @Override
  public Map<String, String> login(@Valid LoginDTO member){

    Authentication authentication = null;
    try{
      authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      member.getMemberEmail(),
                      member.getMemberPassword()
              )
      );
    } catch(BadCredentialsException e){
      throw new BadCredentialsException("이메일 또는 비밀번호를 잘못 입력하셨습니다.");
    }

    CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

    log.info("로그인 성공!");
    log.info("인증에 성공한 사용자의 정보 : {}", user);

    // 토큰 생성
    Map<String, String> tokens = tokenService.generateToken(user.getUsername(), user.getMemberNo());

    // 응답 데이터 구성
    Map<String, String> loginResponse = new HashMap<>();
    loginResponse.put("message", "로그인 성공~~👻");
    loginResponse.put("memberName", user.getMemberName());
    loginResponse.put("memberEmail", user.getUsername());

    // 보안상 비밀번호는 응답에 포함시키지 않는 것이 좋습니다
    // loginResponse.put("memberPassword", user.getPassword());

    // 토큰 정보 추가
    loginResponse.put("accessToken", tokens.get("accessToken"));
    loginResponse.put("refreshToken", tokens.get("refreshToken"));

    return loginResponse;
  }
}