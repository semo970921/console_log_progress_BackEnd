package com.kh.spring.auth.service;

import com.kh.spring.auth.model.dto.LoginDTO;
import jakarta.validation.Valid;

import java.util.Map;

public interface AuthService {

  Map<String, String> login(@Valid LoginDTO member);

}
