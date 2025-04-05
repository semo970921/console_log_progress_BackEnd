package com.kh.spring.member.model.service;

import com.kh.spring.member.model.dto.MemberDTO;

public interface MemberService {

  // 회원가입
  void signUp(MemberDTO member);

}
