package com.kh.spring.token.model.service;

import java.util.Map;

public interface TokenService {
  // 1. AccessToken 만들기
  // 2. RefreshToken 만들기 => 여기까지는 JwtUtil 만듦
  Map<String, String> generateToken(String username, Long memberNo); // 1번과 2번을 Map에 담음(username ==> memberEmail)


  // 3. RefreshToken을 DBTable에 INSERT하기
  // 4. 만료기간이 끝난 리프레시토큰 DELETE 하기
  // 5.  사용자가 리프레시토큰을 가지고 증명하려고 할 때 DB가서 조회해오기
  Map<String, String> refreshToken(String refreshToken);

}