package com.ssafy.soltravel.controller;

import com.ssafy.soltravel.dto.exchange.ExchangeRateRegisterRequestDto;
import com.ssafy.soltravel.dto.exchange.ExchangeRateResponseDto;
import com.ssafy.soltravel.dto.exchange.ExchangeRequestDto;
import com.ssafy.soltravel.dto.exchange.ExchangeResponseDto;
import com.ssafy.soltravel.service.exchange.ExchangeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchange")
@Slf4j
@Tag(name = "Exchange API", description = "환전 관련 API")
public class ExchangeController {

  private final ExchangeService exchangeService;

  /**
   * 실시간 환율 조회
   */
  @GetMapping("/{currency}")
  @Operation(summary = "실시간 환율 조회", description = "특정 통화의 실시간 환율을 조회합니다.", responses = {
      @ApiResponse(responseCode = "200", description = "성공적으로 환율을 조회했습니다.", content = @Content(schema = @Schema(implementation = ExchangeRateResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
      @ApiResponse(responseCode = "404", description = "요청한 통화를 찾을 수 없습니다.", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류입니다.", content = @Content)})
  public ResponseEntity<ExchangeRateResponseDto> getExchangeRate(
      @Parameter(description = "조회할 통화의 코드", example = "USD") @PathVariable String currency) {
    exchangeService.ScheduledGetExchangeRate();
    return ResponseEntity.ok().body(exchangeService.getExchangeRate(currency));
  }

  /**
   * 환전 실행
   */
  @PostMapping
  @Operation(summary = "환전 실행", description = "환전을 실행하고 결과를 반환합니다.", responses = {
      @ApiResponse(responseCode = "200", description = "성공적으로 환전을 완료했습니다.", content = @Content(schema = @Schema(implementation = ExchangeResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
      @ApiResponse(responseCode = "500", description = "환전 금액이 부족합니다.", content = @Content)})
  public ResponseEntity<ExchangeResponseDto> exchange(@RequestBody ExchangeRequestDto requestDto) {
    return ResponseEntity.ok().body(exchangeService.executeExchange(requestDto));
  }

  /**
   * 원하는 환율 저장
   */
  @PostMapping("/register/{accountNo}")
  @Operation(summary = "환율 저장", description = "사용자가 원하는 환율을 저장합니다.", responses = {
      @ApiResponse(responseCode = "200", description = "성공적으로 환율을 저장했습니다.",content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.", content = @Content),
      @ApiResponse(responseCode = "500", description = "서버 오류입니다.", content = @Content)})
  public ResponseEntity<String> setExchangeRate(
      @Parameter(description = "사용자의 계좌 번호", example = "0883473075115544") @PathVariable String accountNo,
      @RequestBody ExchangeRateRegisterRequestDto requestDto) {
    exchangeService.setPreferenceRate(accountNo, requestDto);
    return ResponseEntity.ok().body("register success");
  }

  /**
   * TODO: 최근 환율 조회
   */
}
