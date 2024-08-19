package com.ssafy.soltravel.service.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.soltravel.common.Header;
import com.ssafy.soltravel.dto.account.response.CreateAccountResponseDto;
import com.ssafy.soltravel.dto.account.response.DeleteAccountResponseDto;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class AccountService {

    private static final String BASE_URL = "https://finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit";
    private final WebClient webClient;

    @Value("${external.api.key}")
    private String API_KEY;

    @Value("${external.user.key}")
    private String USER_KEY;

    @Value("${external.account.uniqueNo}")
    private String ACCOUNT_UNIQUE_NO;

    public AccountService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
    }

    public ResponseEntity<CreateAccountResponseDto> createAccount(Long userId) {

        String API_NAME = "createDemandDepositAccount";
        String API_URL = BASE_URL + "/" + API_NAME;

        // 추후에 userId 받아서 userKey 수정
        // 현재는 유저 구현 안되서 임시로 처리함
        Header header = Header.builder()
            .apiName(API_NAME)
            .institutionCode("00100")
            .fintechAppNo("001")
            .apiServiceCode(API_NAME)
            .apiKey(API_KEY)
            .userKey(USER_KEY)
            .build();

        Map<String, Object> body = new HashMap<>();
        body.put("Header", header);
        body.put("accountTypeUniqueNo", ACCOUNT_UNIQUE_NO);

        try {
            // 일반 계좌 DB 저장 로직 유저 완성 시 추가
            ResponseEntity<Map<String, Object>> response = webClient.post()
                .uri(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

            // ObjectMapper 인스턴스 생성
            ObjectMapper objectMapper = new ObjectMapper();

            // REC 부분을 Object 타입으로 받기
            Object recObject = response.getBody().get("REC");

            // 응답 바디를 맵으로 가져온 후 ResponseDto로 변환
            CreateAccountResponseDto responseDto = objectMapper.convertValue(recObject, CreateAccountResponseDto.class);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (WebClientResponseException e) {
            throw e;
        }
    }

    public ResponseEntity<DeleteAccountResponseDto> deleteAccount(String accountNo) {

        String API_NAME = "deleteDemandDepositAccount";
        String API_URL = BASE_URL + "/" + API_NAME;

        Header header = Header.builder()
            .apiName(API_NAME)
            .institutionCode("00100")
            .fintechAppNo("001")
            .apiServiceCode(API_NAME)
            .apiKey(API_KEY)
            .userKey(USER_KEY)
            .build();

        Map<String, Object> body = new HashMap<>();
        body.put("Header", header);
        body.put("accountNo", String.valueOf(accountNo));

        try {
            ResponseEntity<Map<String, Object>> response = webClient.post()
                .uri(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

            // ObjectMapper 인스턴스 생성
            ObjectMapper objectMapper = new ObjectMapper();

            // REC 부분을 Object 타입으로 받기
            Object recObject = response.getBody().get("REC");

            // 응답 바디를 맵으로 가져온 후 ResponseDto로 변환
            DeleteAccountResponseDto responseDto = objectMapper.convertValue(recObject, DeleteAccountResponseDto.class);

            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } catch (WebClientResponseException e) {
            throw e;
        }
    }

}
