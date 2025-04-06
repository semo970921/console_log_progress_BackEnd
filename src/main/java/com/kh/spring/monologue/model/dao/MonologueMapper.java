package com.kh.spring.monologue.model.dao;

import com.kh.spring.monologue.model.vo.Monologue;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MonologueMapper {

  private static final Map<Long, Monologue> database = new HashMap<>();
  private static long idCounter = 1;

  public void insertMonologue(Monologue monologue) {
    monologue.setMonologueNo(idCounter++);
    database.put(monologue.getMonologueNo(), monologue);
  }

  public Monologue selectMonologueById(long id) {
    return database.get(id);
  }

  public List<Monologue> selectAllMonologues() {
    List<Monologue> monologues = new ArrayList<>(database.values());
    // 생성일 기준 내림차순 정렬(최신순)
    monologues.sort(Comparator.comparing(Monologue::getCreatedAt).reversed());
    return monologues;
  }

  public List<Monologue> selectMonologuesByUserId(String userId) {
    List<Monologue> monologues = new ArrayList<>();

    // 해당 사용자의 혼잣말만 필터링
    for (Monologue monologue : database.values()) {
      if (userId != null && userId.equals(monologue.getUserId())) {
        monologues.add(monologue);
      }
    }

    // 생성일 기준 내림차순 정렬(최신순)
    monologues.sort(Comparator.comparing(Monologue::getCreatedAt).reversed());
    return monologues;
  }

  public void updateMonologue(Monologue monologue) {
    database.put(monologue.getMonologueNo(), monologue);
  }

  public void deleteMonologue(long id) {
    database.remove(id);
  }
}