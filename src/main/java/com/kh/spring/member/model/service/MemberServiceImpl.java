package com.kh.spring.member.model.service;

import com.kh.spring.exception.MemberIdDuplicateException;
import com.kh.spring.member.model.dao.MemberMapper;

import com.kh.spring.member.model.dto.SignupDTO;
import com.kh.spring.member.model.vo.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

  private final PasswordEncoder passwordEncoder;
  private final MemberMapper mapper;


  @Override
  public void signUp(SignupDTO member) {

    // 이메일 조회로 멤버가 있는지 확인 후 MemberDTO로.... 그 후 유효성 검사
    SignupDTO searchedMember = mapper.getMemberByMemberEmail(member.getMemberEmail());
    if(searchedMember != null){
      throw new MemberIdDuplicateException("이미 존재하는 아이디입니다.");
    }

    // 입력받은 memberEmail, 암호화된 비번 등등
    // 포함한 새로운 Member 객체 다시 생성
    Member memberValue =  Member.builder()
            .memberName(member.getMemberName())
            .memberEmail(member.getMemberEmail())
            .memberPassword(passwordEncoder.encode(member.getMemberPassword()))
            .role("ROLE_USER")
            .build();

    mapper.signUp(memberValue);

    //log.info("사용자 등록 성공 : {}", member);

  }
}