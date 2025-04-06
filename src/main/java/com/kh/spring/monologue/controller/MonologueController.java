package com.kh.spring.monologue.controller;

import com.kh.spring.monologue.model.service.MonologueService;
import com.kh.spring.monologue.model.vo.Monologue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/monologues")
public class MonologueController {

  @Autowired
  private MonologueService monologueService;

  @PostMapping("/create")
  public ResponseEntity<String> createMonologue(
          @RequestParam("content") String content,
          @RequestParam(value = "file", required = false) MultipartFile file,
          Authentication authentication) {

    System.out.println("혼잣말 저장 요청 받음: " + content);

    if (content == null || content.trim().isEmpty()) {
      return ResponseEntity.badRequest().body("혼잣말 내용을 입력해주세요.");
    }

    try {
      // 인증된 사용자 정보 가져오기 (인증이 없는 경우 null 처리)
      String userId = (authentication != null) ? authentication.getName() : "anonymous";
      System.out.println("인증된 사용자: " + userId);

      monologueService.saveMonologue(content, file, userId);
      System.out.println("혼잣말 저장 성공. 사용자: " + userId);
      return ResponseEntity.ok("혼잣말이 성공적으로 저장되었습니다.");
    } catch (Exception e) {
      System.out.println("혼잣말 저장 실패: " + e.getMessage());
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("저장 중 오류가 발생했습니다. 다시 시도해주세요.");
    }
  }

  @GetMapping("/list")
  public ResponseEntity<List<Monologue>> getMonologues(Authentication authentication) {
    System.out.println("혼잣말 목록 요청 받음");
    try {
      // 인증된 사용자 정보 가져오기 (인증이 없는 경우 null 처리)
      String userId = (authentication != null) ? authentication.getName() : "anonymous";
      System.out.println("인증된 사용자: " + userId);

      // 사용자별 혼잣말 목록 조회
      List<Monologue> monologues = monologueService.getMonologuesByUserId(userId);
      System.out.println("혼잣말 목록 조회 성공. 사용자: " + userId + ", 개수: " + monologues.size());
      return ResponseEntity.ok(monologues);
    } catch (Exception e) {
      System.out.println("혼잣말 목록 조회 실패: " + e.getMessage());
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Monologue> getMonologueById(@PathVariable("id") long id, Authentication authentication) {
    System.out.println("혼잣말 상세 조회 요청 받음. ID: " + id);
    try {
      Monologue monologue = monologueService.getMonologueById(id);

      // 존재하지 않는 혼잣말인 경우
      if (monologue == null) {
        System.out.println("혼잣말 상세 조회 실패: 해당 ID의 혼잣말 없음. ID: " + id);
        return ResponseEntity.notFound().build();
      }

      // 인증된 사용자 정보 가져오기 (인증이 없는 경우 null 처리)
      String userId = (authentication != null) ? authentication.getName() : "anonymous";

      // 본인 혼잣말이 아닌 경우 접근 거부
      if (!userId.equals(monologue.getUserId()) && !"anonymous".equals(userId)) {
        System.out.println("혼잣말 상세 조회 실패: 권한 없음. 요청 사용자: " + userId + ", 혼잣말 소유자: " + monologue.getUserId());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
      }

      System.out.println("혼잣말 상세 조회 성공. ID: " + id);
      return ResponseEntity.ok(monologue);
    } catch (Exception e) {
      System.out.println("혼잣말 상세 조회 중 오류 발생: " + e.getMessage());
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateMonologue(
          @PathVariable("id") long id,
          @RequestParam("content") String content,
          @RequestParam(value = "file", required = false) MultipartFile file,
          Authentication authentication) {

    System.out.println("혼잣말 수정 요청 받음. ID: " + id + ", 내용: " + content);

    if (content == null || content.trim().isEmpty()) {
      return ResponseEntity.badRequest().body("혼잣말 내용을 입력해주세요.");
    }

    try {
      // 혼잣말 조회
      Monologue monologue = monologueService.getMonologueById(id);

      // 존재하지 않는 혼잣말인 경우
      if (monologue == null) {
        System.out.println("혼잣말 수정 실패: 해당 ID의 혼잣말 없음. ID: " + id);
        return ResponseEntity.notFound().build();
      }

      // 인증된 사용자 정보 가져오기 (인증이 없는 경우 null 처리)
      String userId = (authentication != null) ? authentication.getName() : "anonymous";

      // 본인 혼잣말이 아닌 경우 접근 거부
      if (!userId.equals(monologue.getUserId()) && !"anonymous".equals(userId)) {
        System.out.println("혼잣말 수정 실패: 권한 없음. 요청 사용자: " + userId + ", 혼잣말 소유자: " + monologue.getUserId());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("수정 권한이 없습니다.");
      }

      boolean updated = monologueService.updateMonologue(id, content, file);
      if (updated) {
        System.out.println("혼잣말 수정 성공. ID: " + id);
        return ResponseEntity.ok("혼잣말이 성공적으로 수정되었습니다.");
      } else {
        System.out.println("혼잣말 수정 실패: 업데이트 중 오류 발생. ID: " + id);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수정 중 오류가 발생했습니다.");
      }
    } catch (Exception e) {
      System.out.println("혼잣말 수정 중 오류 발생: " + e.getMessage());
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("수정 중 오류가 발생했습니다. 다시 시도해주세요.");
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteMonologue(
          @PathVariable("id") long id,
          Authentication authentication) {

    System.out.println("혼잣말 삭제 요청 받음. ID: " + id);

    try {
      // 혼잣말 조회
      Monologue monologue = monologueService.getMonologueById(id);

      // 존재하지 않는 혼잣말인 경우
      if (monologue == null) {
        System.out.println("혼잣말 삭제 실패: 해당 ID의 혼잣말 없음. ID: " + id);
        return ResponseEntity.notFound().build();
      }

      // 인증된 사용자 정보 가져오기 (인증이 없는 경우 null 처리)
      String userId = (authentication != null) ? authentication.getName() : "anonymous";

      // 본인 혼잣말이 아닌 경우 접근 거부
      if (!userId.equals(monologue.getUserId()) && !"anonymous".equals(userId)) {
        System.out.println("혼잣말 삭제 실패: 권한 없음. 요청 사용자: " + userId + ", 혼잣말 소유자: " + monologue.getUserId());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제 권한이 없습니다.");
      }

      boolean deleted = monologueService.deleteMonologue(id);
      if (deleted) {
        System.out.println("혼잣말 삭제 성공. ID: " + id);
        return ResponseEntity.ok("혼잣말이 성공적으로 삭제되었습니다.");
      } else {
        System.out.println("혼잣말 삭제 실패: 삭제 중 오류 발생. ID: " + id);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 중 오류가 발생했습니다.");
      }
    } catch (Exception e) {
      System.out.println("혼잣말 삭제 중 오류 발생: " + e.getMessage());
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("삭제 중 오류가 발생했습니다. 다시 시도해주세요.");
    }
  }
}