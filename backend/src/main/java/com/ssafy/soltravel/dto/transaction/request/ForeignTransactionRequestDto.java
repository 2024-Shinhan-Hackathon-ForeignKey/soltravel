package com.ssafy.soltravel.dto.transaction.request;

import lombok.Data;

@Data
public class ForeignTransactionRequestDto {

  private Double transactionBalance;
  private String transactionSummary;
}
