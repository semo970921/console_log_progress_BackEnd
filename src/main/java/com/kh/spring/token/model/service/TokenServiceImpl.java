package com.kh.spring.token.model.service;

import com.kh.spring.auth.util.JwtUtil;
import com.kh.spring.token.model.dao.TokenMapper;
import com.kh.spring.token.vo.RefreshToken;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

  private final JwtUtil tokenUtil;
  private final TokenMapper tokenMapper;

  @Override
  public Map<String, String> generateToken(String username, Long memberNo) {

    // 1. AccessToken 만들기
    // 2. RefreshToken 만들기 => 여기까지는 JwtUtil 만듦
    Map<String, String> tokens = createToken(username);

    // 3번. RefreshToken을 DBTable에 INSERT하기
    // (memberNo, refreshToken, expiration)
    saveToken(tokens.get("refreshToken"), memberNo);



    // 4번. 만료기간이 끝난 리프레시토큰 DELETE 하기
    tokenMapper.deleteExpiredRefreshToken(System.currentTimeMillis());

    return tokens;
  }

  private void saveToken(String refreshToken, Long memberNo) {
    RefreshToken token = RefreshToken.builder()
            .token(refreshToken)
            .memberNo(memberNo)
            .expiration(System.currentTimeMillis() + 36000000L * 72)
            .build();

    tokenMapper.saveToken(token);

  }


  private Map<String, String> createToken(String username){

    String accessToken = tokenUtil.getAccessToken(username);
    String refreshToken = tokenUtil.getRefreshToken(username);

    Map<String, String> tokens = new HashMap<>();
    tokens.put("accessToken", accessToken);
    tokens.put("refreshToken", refreshToken);



    return tokens;

  }

  @Override
  public Map<String, String> refreshToken(String refreshToken){
    RefreshToken token = RefreshToken.builder().token(refreshToken).build();
    RefreshToken responseToken = tokenMapper.findByToken(token);

    if(refreshToken == null || token.getExpiration() < System.currentTimeMillis()) {
      throw new RuntimeException("유효하지 않은 토큰입니다");
    }

    String username = getUsernameByToken(refreshToken);
    Long memberNo = responseToken.getMemberNo();

    return generateToken(username,memberNo);

  }

  private String getUsernameByToken(String refreshToken) {

    Claims claims = tokenUtil.parseJwt(refreshToken);
    return claims.getSubject();

  }

}