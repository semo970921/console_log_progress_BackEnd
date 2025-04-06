package com.kh.spring.auth.controller;

import com.kh.spring.auth.model.dto.LoginDTO;
import com.kh.spring.auth.service.AuthService;
import com.kh.spring.token.model.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody @Valid LoginDTO member){

    Map<String, String> loginResponse = authService.login(member);
    return ResponseEntity.ok(loginResponse);
  }


  @PostMapping("/refresh")
  public ResponseEntity<?> refresh(@RequestBody Map<String, String> token){
    String refreshToken = token.get("refreshToken");

    Map<String, String> newToken = tokenService.refreshToken(refreshToken);

    return ResponseEntity.status(HttpStatus.CREATED).body(newToken);

  }


}