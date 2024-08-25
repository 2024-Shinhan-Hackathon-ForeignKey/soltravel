package com.ssafy.soltravel.dto.exchange;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeCurrencyDto {

  private long amount;
  private Double exchangeRate;
  private String currency;
}
