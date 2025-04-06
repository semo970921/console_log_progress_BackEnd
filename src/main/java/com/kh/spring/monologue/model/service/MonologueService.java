package com.kh.spring.monologue.model.service;

import com.kh.spring.monologue.model.vo.Monologue;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MonologueService {
  void saveMonologue(String content, MultipartFile file, String userId) throws Exception;
  List<Monologue> getAllMonologues() throws Exception;
  List<Monologue> getMonologuesByUserId(String userId) throws Exception;
  Monologue getMonologueById(long id) throws Exception;
  boolean updateMonologue(long id, String content, MultipartFile file) throws Exception;
  boolean deleteMonologue(long id) throws Exception;
}