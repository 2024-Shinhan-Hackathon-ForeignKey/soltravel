package com.ssafy.soltravel.dto.exchange;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRequestDto {

  private long accountId;
  private String accountNo;
  private String exchangeCurrency;
  private long exchangeAmount;
  private Double exchangeRate;
}
