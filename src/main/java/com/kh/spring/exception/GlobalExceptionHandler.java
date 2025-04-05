package com.kh.spring.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.sasl.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  private ResponseEntity<Map<String, String>> makeResponseEntity(RuntimeException e,
                                                                 HttpStatus status){
    Map<String, String> error = new HashMap();
    error.put("message", e.getMessage());
    return ResponseEntity.status(status).body(error);
  }

  @ExceptionHandler(MemberIdDuplicateException.class)
  public ResponseEntity<Map<String, String>> handleDuplicateMemberId(MemberIdDuplicateException e){

    return makeResponseEntity(e, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CustomAuthenticationException.class)
  public ResponseEntity<Map<String, String>> handleAuthenticationException(CustomAuthenticationException e){
    Map<String, String> error = new HashMap();
    error.put("error-message", e.getMessage());
    return ResponseEntity.badRequest().body(error);
  }

}
