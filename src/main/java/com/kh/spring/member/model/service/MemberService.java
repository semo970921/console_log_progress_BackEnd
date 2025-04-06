package com.kh.spring.member.model.service;

import com.kh.spring.member.model.dto.SignupDTO;

public interface MemberService {

  // 회원가입
  void signUp(SignupDTO member);

}