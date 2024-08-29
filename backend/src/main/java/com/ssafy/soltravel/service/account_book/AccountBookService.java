package com.ssafy.soltravel.service.account_book;

import com.ssafy.soltravel.domain.ForeignAccount;
import com.ssafy.soltravel.dto.account_book.AccountHistorySaveRequestDto;
import com.ssafy.soltravel.dto.account_book.ReceiptAnalysisDto;
import com.ssafy.soltravel.dto.account_book.ReceiptUploadRequestDto;
import com.ssafy.soltravel.exception.ForeignAccountNotFoundException;
import com.ssafy.soltravel.exception.LackOfBalanceException;
import com.ssafy.soltravel.mapper.AccountBookMapper;
import com.ssafy.soltravel.repository.ForeignAccountRepository;
import com.ssafy.soltravel.service.AwsFileService;
import com.ssafy.soltravel.service.GPTService;
import com.ssafy.soltravel.util.SecurityUtil;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountBookService {

  private final AwsFileService fileService;
  private final ClovaOcrService ocrService;
  private final GPTService gptService;
  private final ForeignAccountRepository foreignAccountRepository;
  private final CashHistoryService cashHistoryService;

  /*
  * 영수증 업로드 &
  */
  public ReceiptAnalysisDto uploadReceipt(ReceiptUploadRequestDto requestDto)
      throws IOException {

    // 필요 변수 정의
    MultipartFile file = requestDto.getFile();
    Long userId = SecurityUtil.getCurrentUserId();

    // userId로 파일 저장(S3)
    String uploadUrl = fileService.saveReciept(file, userId);

    // Clova OCR 사용
    ResponseEntity<Map<String, Object>> response = ocrService.execute(requestDto, uploadUrl);

    // 챗지피티한테 정리시키기
    String receiptInfoString = gptService.askChatGPT(response.getBody().toString());

    // String(JSON) -> 객체 변환후 return
    return AccountBookMapper.convertJSONToItemAnalysisDto(receiptInfoString);
  }


  /*
  * 영수증 정보로 가계부 등록
  */
  public void saveAccountHistory(AccountHistorySaveRequestDto requestDto)
      throws LackOfBalanceException {

    ForeignAccount foreignAccount = foreignAccountRepository.findByAccountNo(requestDto.getAccountNo())
        .orElseThrow(() -> new ForeignAccountNotFoundException(requestDto.getAccountNo()));

    Double newBalance = cashHistoryService.payCash(
        foreignAccount, requestDto.getPaid()
    );

//    return AccountHistorySaveResponseDto
  }



}
