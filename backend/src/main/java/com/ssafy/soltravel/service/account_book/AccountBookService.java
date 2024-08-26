package com.ssafy.soltravel.service.account_book;

import com.ssafy.soltravel.dto.account_book.ReceiptUploadResponseDto;
import com.ssafy.soltravel.service.AwsFileService;
import com.ssafy.soltravel.util.LogUtil;
import com.ssafy.soltravel.util.SecurityUtil;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountBookService {

  private final AwsFileService fileService;

  public ReceiptUploadResponseDto uploadReceipt(MultipartFile file) throws IOException {
    Long userId = SecurityUtil.getCurrentUserId();
    String uploadUrl = fileService.savePhoto(file, userId);
    LogUtil.info("upload", uploadUrl);
    return ReceiptUploadResponseDto.builder()
        .message("영수증 사진 업로드 완료")
        .uploadUrl(uploadUrl)
        .build();
  }

}
