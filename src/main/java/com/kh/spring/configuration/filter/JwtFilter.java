package com.kh.spring.configuration.filter;

import com.kh.spring.auth.model.vo.CustomUserDetails;
import com.kh.spring.auth.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil util;
  private final UserDetailsService userService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {

    // 토큰 검증
    String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

    if(authorization == null || !authorization.startsWith("Bearer ")) {
      filterChain.doFilter(request,  response);
      return;
    }

    // 부분만 잘라와
    String token = authorization.split(" ")[1];
    log.info("토큰값 : {}", token);

    // 이제 해야할 작업
    // 1. 서버에서 관리하는 비밀키로 만들어진것이 맞는가?
    // 2. 유효기간이 안지났는가?
    // 직접하는건 아니고 라이브러리 불러옴 // jwtUtil
    try {
      Claims claims = util.parseJwt(token);
      String username = claims.getSubject();

      CustomUserDetails user = (CustomUserDetails)userService.loadUserByUsername(username);

      UsernamePasswordAuthenticationToken authentication
              = new UsernamePasswordAuthenticationToken(user, null,user.getAuthorities());
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      SecurityContextHolder.getContext().setAuthentication(authentication);


    } catch(ExpiredJwtException e) { // 토큰 기간이 지났다면
      log.info("만료됨");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("만료된 토큰입니다.");
      return; // 더이상 컨트롤러에 보낼 수 없도록 필터를 가지고 막아줌
    } catch(JwtException e) {
      log.info("유효하지 않은 토큰값");
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("유효하지 않은 토큰입니다.");
      return;
    }

    // filterChain으로 넘김
    filterChain.doFilter(request, response);

  }
}