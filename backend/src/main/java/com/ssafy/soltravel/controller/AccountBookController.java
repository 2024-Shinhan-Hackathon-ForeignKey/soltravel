package com.ssafy.soltravel.controller;

import com.ssafy.soltravel.dto.account_book.ReceiptUploadResponseDto;
import com.ssafy.soltravel.service.account_book.AccountBookService;
import com.ssafy.soltravel.util.LogUtil;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/account-book")
@RequiredArgsConstructor
public class AccountBookController {

  private final AccountBookService accountBookService;

  @PostMapping("/upload/receipt")
  public ResponseEntity<?> uploadReceipt(@RequestParam("file") MultipartFile file) throws IOException {

    LogUtil.info("requested", file.getName());
    ReceiptUploadResponseDto response = accountBookService.uploadReceipt(file);
    return ResponseEntity.ok().body(response);
  }

}
