package com.kh.spring.member.model.dao;

import com.kh.spring.member.model.dto.SignupDTO;
import com.kh.spring.member.model.vo.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {

  // 회원가입
  @Insert("INSERT INTO MY_MEMBER(MEMBER_NO, MEMBER_EMAIL, MEMBER_NAME, MEMBER_PASSWORD, ROLE) VALUES(SEQ_MY_MEMBER.NEXTVAL, #{memberEmail}, #{memberName}, #{memberPassword}, #{role})")
  int signUp(Member member);

  @Select("SELECT MEMBER_NO memberNo, MEMBER_EMAIL memberEmail, MEMBER_NAME memberName, MEMBER_PASSWORD memberPassword," +
          " ROLE role FROM MY_MEMBER WHERE MEMBER_EMAIL = #{memberEmail}")
  SignupDTO getMemberByMemberEmail(String memberEmail);

}
