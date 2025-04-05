package com.kh.spring.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secretKey;
  private SecretKey key; // 토큰 만들 때 사용

  @PostConstruct
  public void init(){
    byte[] keyArr = Base64.getDecoder().decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyArr);
  }

  // 토큰 만들기
  public String getAccessToken(String username) {

    return Jwts.builder().subject(username)
            .issuedAt(new Date()) // 발급일  //지금 발급
            .expiration(new Date(System.currentTimeMillis() + 36000000L * 24)) //만료일
            .signWith(key) // 서명
            .compact();
  }


  public String getRefreshToken(String username) {

    return Jwts.builder()
            .subject(username)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 36000000L * 24 *3))
            .signWith(key)
            .compact();
  }


  // 토큰 검증해주는 메서드
  public Claims parseJwt(String token) {

    return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
  }

}
