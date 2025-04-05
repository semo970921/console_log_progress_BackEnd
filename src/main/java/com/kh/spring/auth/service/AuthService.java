package com.kh.spring.auth.service;

import com.kh.spring.member.model.dto.MemberDTO;

import java.util.Map;

public interface AuthService {

  Map<String, String> login(MemberDTO member);

}
