package com.kh.spring.member.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@Builder
public class Member {

  private Long memberNo;
  private String memberName;
  private String memberEmail;
  private String memberPassword;
  private String role;

}
