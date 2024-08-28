package com.ssafy.soltravel.dto.account.request;

import com.ssafy.soltravel.domain.Enum.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateAccountRequestDto {

    @Schema(description = "계좌 유형 (개인 : INDIVIDUAL, 그룹 : GROUP)", example = "GROUP")
    private AccountType accountType;

    @Schema(description = "계좌 비밀번호", example = "1234")
    private String accountPassword;

    @Schema(description = "모임통장 이름(모임통장인 경우)", example = "SolTravel 모임통장")
    private String groupName;

    @Schema(description = "통화 코드 (예: KRW, USD)", example = "USD")
    private String currencyCode;
}
