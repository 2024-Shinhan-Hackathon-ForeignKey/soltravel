package com.ssafy.soltravel.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthSMSSendResponseDto {

  private String phone;
  String statusMessage;
}
