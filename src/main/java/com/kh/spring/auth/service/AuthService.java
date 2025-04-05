package com.kh.spring.auth.service;

import com.kh.spring.member.model.dto.SignupDTO;
import jakarta.validation.Valid;

import java.util.Map;

public interface AuthService {

  Map<String, String> login(@Valid SignupDTO member);

}
