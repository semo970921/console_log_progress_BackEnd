package com.kh.spring.exception;

public class MemberIdDuplicateException extends RuntimeException{
  public MemberIdDuplicateException(String message){
    super(message);
  }
}
