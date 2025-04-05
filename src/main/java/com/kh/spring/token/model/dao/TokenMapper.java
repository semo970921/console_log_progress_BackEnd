package com.kh.spring.token.model.dao;

import com.kh.spring.token.vo.RefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenMapper {

  void saveToken(RefreshToken token);
  RefreshToken findByToken(RefreshToken token);
  void deleteExpiredRefreshToken(Long now);

}
