package com.kh.spring.monologue.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MonologueDTO {
  private String content;
  private String weather;
  private String filePath;

}
