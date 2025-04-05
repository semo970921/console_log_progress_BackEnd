package com.kh.spring.auth.model.vo;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Value
@Builder
@Getter
@ToString
public class CustomUserDetails implements UserDetails {

  private Long memberNo;
  private String memberEmail;
  private String memberPassword;
  private String memberName;
  private String role;
  private Collection<? extends GrantedAuthority> authorities;


  @Override
  public String getPassword() {
    return memberPassword;
  }

  @Override
  public String getUsername() {
    return memberEmail;
  }
}
