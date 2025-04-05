package com.kh.spring.token.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RefreshToken {

  private String token;   // 토큰 문자뎔
  private Long memberNo;  // 회원 번호
  private Long expiration;  // 만료시간

}
