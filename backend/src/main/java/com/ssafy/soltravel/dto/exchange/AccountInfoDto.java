package com.ssafy.soltravel.dto.exchange;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfoDto {

  private String accountNo;
  private Long accountId;
  private long amount;
  private long balance;
}
