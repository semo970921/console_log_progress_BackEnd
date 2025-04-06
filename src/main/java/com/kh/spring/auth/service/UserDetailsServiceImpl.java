package com.kh.spring.auth.service;

import com.kh.spring.auth.model.dto.LoginDTO;
import com.kh.spring.auth.model.vo.CustomUserDetails;
import com.kh.spring.exception.CustomAuthenticationException;
import com.kh.spring.member.model.dao.MemberMapper;
import com.kh.spring.member.model.dto.SignupDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final MemberMapper mapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    SignupDTO user = mapper.getMemberByMemberEmail(username);

    if(user == null){
      throw new CustomAuthenticationException("존재하지 않는 사용자 입니다");

    }

    return CustomUserDetails.builder()
            .memberNo(user.getMemberNo())
            .memberEmail(user.getMemberEmail())
            .memberName(user.getMemberName())
            .memberPassword(user.getMemberPassword())
            .role(user.getRole())
            .build();
  }


}