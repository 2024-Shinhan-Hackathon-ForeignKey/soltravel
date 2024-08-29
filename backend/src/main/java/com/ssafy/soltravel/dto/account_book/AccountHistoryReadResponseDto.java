package com.ssafy.soltravel.dto.account_book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountHistoryReadResponseDto {
  private String accountNo;
  private Integer transactionCount;

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  static class DayAccountHistory{
    private Double totalExpenditure;
    private Double totalIncome;
  }

}
