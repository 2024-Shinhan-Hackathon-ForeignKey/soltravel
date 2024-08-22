package com.ssafy.soltravel.domain.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Token")
@AllArgsConstructor
@Getter
@Setter
public class RedisToken {

  @Id
  private Long userId;
  private String refreshToken;
}


