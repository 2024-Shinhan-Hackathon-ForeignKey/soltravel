package com.ssafy.soltravel.dto.exchange;

import lombok.Data;

@Data
public class ExchangeRequestDto {

  private String accountNo;
  private String exchangeCurrency;
  private String exchangeAmount;
}
