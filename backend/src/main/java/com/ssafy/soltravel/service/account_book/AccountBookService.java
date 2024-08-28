package com.ssafy.soltravel.service.account_book;

import com.ssafy.soltravel.dto.account_book.ReceiptUploadRequestDto;
import com.ssafy.soltravel.dto.account_book.ReceiptUploadResponseDto;
import com.ssafy.soltravel.service.AwsFileService;
import com.ssafy.soltravel.service.GPTService;
import com.ssafy.soltravel.util.LogUtil;
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

  public ReceiptUploadResponseDto uploadReceipt(ReceiptUploadRequestDto requestDto)
      throws IOException {

    // 필요 변수 정의
    MultipartFile file = requestDto.getFile();
    Long userId = SecurityUtil.getCurrentUserId();

    // userId로 파일 저장(S3)
    String uploadUrl = fileService.saveReciept(file, userId);
    LogUtil.info("upload", uploadUrl);

    // Clova OCR 사용
    ResponseEntity<Map<String, Object>> response = ocrService.execute(requestDto, uploadUrl);

    //TODO: 챗 지피티한테 파싱 시키기
    String question = createQuestion(response.getBody());
    String reseponse = gptService.askChatGPT(question);
    LogUtil.info("reseponse", reseponse);

    return ReceiptUploadResponseDto.builder()
        .message("영수증 사진 업로드 완료")
        .uploadUrl(uploadUrl)
        .build();
  }

  private String createQuestion(Map<String, Object> map) {
    return String.format("%s 이 데이터에서 판매점 이름(store), 각 물품의 이름과 가격(name, price), 총 가격(total price)을 JSON 형태로 정리해줘", map);
  }

}
