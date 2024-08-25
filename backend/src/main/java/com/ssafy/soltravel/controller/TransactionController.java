package com.ssafy.soltravel.controller;

import com.ssafy.soltravel.dto.transaction.TransactionHistoryDto;
import com.ssafy.soltravel.dto.transaction.request.ForeignTransactionRequestDto;
import com.ssafy.soltravel.dto.transaction.request.TransactionHistoryRequestDto;
import com.ssafy.soltravel.dto.transaction.request.TransactionRequestDto;
import com.ssafy.soltravel.dto.transaction.request.TransferRequestDto;
import com.ssafy.soltravel.dto.transaction.response.DepositResponseDto;
import com.ssafy.soltravel.dto.transaction.response.TransferHistoryResponseDto;
import com.ssafy.soltravel.service.transaction.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
@Tag(name = "Transaction API", description = "거래 관련 API")
public class TransactionController {

    private final TransactionService transactionService;

    // 계좌 입금
    @PostMapping("/{accountNo}/deposit")
    public ResponseEntity<DepositResponseDto> postAccountDeposit(
        @PathVariable String accountNo,
        @RequestBody TransactionRequestDto requestDto
    ) {
        ResponseEntity<DepositResponseDto> response = transactionService.postAccountDeposit(accountNo, requestDto);

        return response;
    }

    // 계좌 출금
    @PostMapping("/{accountNo}/withdraw")
    public ResponseEntity<DepositResponseDto> postAccountWithdrawal(
        @PathVariable String accountNo,
        @RequestBody TransactionRequestDto requestDto
    ) {
        ResponseEntity<DepositResponseDto> response = transactionService.postAccountWithdrawal(accountNo, requestDto);

        return response;
    }

    // 계좌 이체
    @PostMapping("/{accountNo}/transfer")
    public ResponseEntity<List<TransferHistoryResponseDto>> postAccountTransfer(
        @PathVariable String accountNo,
        @RequestBody TransferRequestDto requestDto
    ) {
        ResponseEntity<List<TransferHistoryResponseDto>> response = transactionService.postAccountTransfer(accountNo, requestDto);

        return response;
    }


    // 거래 내역 조회
    @GetMapping("/{accountNo}/history")
    public ResponseEntity<List<TransactionHistoryDto>> getHistoryByAccountNo(
        @PathVariable String accountNo,
        @RequestBody TransactionHistoryRequestDto requestDto
    ) {

        ResponseEntity<List<TransactionHistoryDto>> response = transactionService.getHistoryByAccountNo(accountNo, requestDto);

        return response;
    }

    /**
     * 외화 계좌 입금
     */
    @PostMapping("/foreign/{accountNo}/deposit")
    @Operation(summary = "외화 계좌 입금", description = "외화 계좌에 입금합니다.")
    public ResponseEntity<DepositResponseDto> postForeignAccountDeposit(
        @Parameter(description = "사용자의 계좌 번호", example = "0887850232491646")
        @PathVariable String accountNo,
        @RequestBody ForeignTransactionRequestDto requestDto
    ) {
        return ResponseEntity.ok().body(transactionService.postForeignDeposit(accountNo, requestDto));
    }

    /**
     * 외화 계좌 거래 내역 조회
     * getForeignHistoryByAccountNo
     */
    @GetMapping("/foreign/{accountNo}/history")
    @Operation(summary = "외화 계좌 거래 내역 조회", description = "외화 계좌의 거래 내역을 조회합니다.")
    public ResponseEntity<List<TransactionHistoryDto>> getForeignHistoryByAccountNo(
        @Parameter(description = "사용자의 계좌 번호", example = "0887850232491646")
        @PathVariable String accountNo,
        @RequestBody TransactionHistoryRequestDto requestDto
    ) {

        return ResponseEntity.ok().body(transactionService.getForeignHistoryByAccountNo(accountNo, requestDto));
    }
}
