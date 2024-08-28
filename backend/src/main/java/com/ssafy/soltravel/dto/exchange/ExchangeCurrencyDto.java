package com.ssafy.soltravel.dto.exchange;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeCurrencyDto {

  @Schema(description = "환전한 원화 금액", example = "1000")
  private long amount;

  @Schema(description = "적용된 환율", example = "1333.40")
  private Double exchangeRate;

  @Schema(description = "환전된 외화의 통화 코드", example = "USD")
  private String currencyCode;
}
