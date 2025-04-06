package com.kh.spring.monologue.model.service;

import com.kh.spring.monologue.model.dao.MonologueMapper;
import com.kh.spring.monologue.model.vo.Monologue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MonologueServiceImpl implements MonologueService {

  @Autowired
  private MonologueMapper monologueMapper;

  private static final String UPLOAD_DIR = "uploads/";

  @Override
  public void saveMonologue(String content, MultipartFile file, String userId) throws IOException {
    Monologue monologue = new Monologue();
    monologue.setContent(content);
    monologue.setCreatedAt(LocalDateTime.now());
    monologue.setUpdatedAt(LocalDateTime.now());
    monologue.setUserId(userId); // 사용자 ID 설정

    // 현재 날씨 정보 설정 (실제 구현에서는 날씨 API 사용)
    String currentWeather = "맑음"; // 기본값
    monologue.setWeather(currentWeather);

    if (file != null && !file.isEmpty()) {
      String originalFilename = file.getOriginalFilename();
      String filePath = UPLOAD_DIR + originalFilename;
      File dest = new File(filePath);

      // 디렉토리가 없으면 생성
      if (!dest.getParentFile().exists()) {
        dest.getParentFile().mkdirs();
      }

      file.transferTo(dest);
      monologue.setAttachmentNo(filePath);
    }

    monologueMapper.insertMonologue(monologue);
  }

  @Override
  public List<Monologue> getAllMonologues() {
    return monologueMapper.selectAllMonologues();
  }

  @Override
  public List<Monologue> getMonologuesByUserId(String userId) {
    return monologueMapper.selectMonologuesByUserId(userId);
  }

  @Override
  public Monologue getMonologueById(long id) throws Exception {
    return monologueMapper.selectMonologueById(id);
  }

  @Override
  public boolean updateMonologue(long id, String content, MultipartFile file) throws Exception {
    // 기존 혼잣말 조회
    Monologue monologue = monologueMapper.selectMonologueById(id);
    if (monologue == null) {
      return false;
    }

    // 내용 업데이트
    monologue.setContent(content);
    monologue.setUpdatedAt(LocalDateTime.now());

    // 파일이 있으면 업데이트
    if (file != null && !file.isEmpty()) {
      // 기존 파일이 있으면 삭제
      if (monologue.getAttachmentNo() != null) {
        File oldFile = new File(monologue.getAttachmentNo());
        if (oldFile.exists()) {
          oldFile.delete();
        }
      }

      // 새 파일 저장
      String originalFilename = file.getOriginalFilename();
      String filePath = UPLOAD_DIR + originalFilename;
      File dest = new File(filePath);

      // 디렉토리가 없으면 생성
      if (!dest.getParentFile().exists()) {
        dest.getParentFile().mkdirs();
      }

      file.transferTo(dest);
      monologue.setAttachmentNo(filePath);
    }

    // DB 업데이트
    monologueMapper.updateMonologue(monologue);
    return true;
  }

  @Override
  public boolean deleteMonologue(long id) throws Exception {
    // 기존 혼잣말 조회
    Monologue monologue = monologueMapper.selectMonologueById(id);
    if (monologue == null) {
      return false;
    }

    // 첨부 파일이 있으면 삭제
    if (monologue.getAttachmentNo() != null) {
      File file = new File(monologue.getAttachmentNo());
      if (file.exists()) {
        file.delete();
      }
    }

    // DB에서 삭제
    monologueMapper.deleteMonologue(id);
    return true;
  }
}