package com.kh.spring.monologue.model.vo;

import java.time.LocalDateTime;

public class Monologue {
  private long monologueNo;
  private String content;
  private String weather;
  private String attachmentNo;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private String userId; // 추가: 사용자 식별자

  public long getMonologueNo() {
    return monologueNo;
  }

  public void setMonologueNo(long monologueNo) {
    this.monologueNo = monologueNo;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getWeather() {
    return weather;
  }

  public void setWeather(String weather) {
    this.weather = weather;
  }

  public String getAttachmentNo() {
    return attachmentNo;
  }

  public void setAttachmentNo(String attachmentNo) {
    this.attachmentNo = attachmentNo;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}