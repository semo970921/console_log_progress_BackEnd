package com.kh.spring.auth.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginDTO {

  @NotBlank(message="이메일을 입력해주세요.")
  private String memberEmail;

  @NotBlank(message="비밀번호를 입력해주세요.")
  private String memberPassword;

}