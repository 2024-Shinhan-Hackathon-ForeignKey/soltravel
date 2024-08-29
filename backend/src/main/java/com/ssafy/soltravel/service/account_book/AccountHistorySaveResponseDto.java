package com.ssafy.soltravel.service.account_book;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountHistorySaveResponseDto {

  @Schema(description = "응답 메세지", example = "가계부가 등록되었습니다.")
  private String message;

  @Schema(description = "현금 잔액, 인출된 총 현금에서 사용한 현금을 뺀 값입니다.", example = "25.2")
  private Double cashBalance;

}
