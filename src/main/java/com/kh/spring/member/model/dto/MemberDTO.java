package com.kh.spring.member.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {

  private Long memberNo; // 시퀀스 넘버

  @NotBlank(message="이름을 입력해주세요.")
  private String memberName;

  @NotBlank(message="이메일을 입력해주세요.")
  @Pattern(
      regexp = "^[A-Za-z0-9]([-_.]?[A-Za-z0-9])*@[A-Za-z0-9]([-_.]?[A-Za-z0-9])*\\.[A-Za-z]{2,3}$",
      message = "유효한 이메일 주소를 입력해주세요."
  )
  private String memberEmail;

  @NotBlank(message="비밀번호를 입력해주세요.")
  @Pattern(
          regexp = "^[A-Za-z0-9]{5,}$",
          message = "비밀번호는 영문 포함, 최소 5자 이상이어야 합니다."
  )
  private String memberPassword;

  // @NotBlank(message="비밀번호 확인을 입력해주세요.")
  // 리액트로는 비밀번호 확인을 했는데..
  // 시큐러티로는 어떻게 확인을...
  //  private String confirmPassword; // 비밀번호 확인
  // 종엽쓰왈 : 비밀번호 확인은 사용자 편의를 위한것이기에
  //           앞단에서만 해도 괜찮고 굳이 서버에서 할 필요 없다


  private String role; // Security를 위한 Role

}
