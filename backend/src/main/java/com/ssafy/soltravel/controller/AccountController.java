package com.ssafy.soltravel.controller;

import com.ssafy.soltravel.dto.account.response.CreateAccountResponseDto;
import com.ssafy.soltravel.dto.account.response.DeleteAccountResponseDto;
import com.ssafy.soltravel.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/{userId}")
    public ResponseEntity<CreateAccountResponseDto> createAccount(@PathVariable Long userId) {

        ResponseEntity<CreateAccountResponseDto> responseEntity = accountService.createAccount(userId);

        return responseEntity;
    }

    @DeleteMapping("/{accountNo}")
    public ResponseEntity<DeleteAccountResponseDto> deleteAccount(@PathVariable String accountNo) {

        ResponseEntity<DeleteAccountResponseDto> responseEntity = accountService.deleteAccount(accountNo);

        return responseEntity;
    }

}
