package com.ssafy.soltravel.dto.account.request;

import com.ssafy.soltravel.domain.Enum.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Data;

@Data
public class CreateAccountRequestDto {

    @Schema(description = "계좌 유형 (개인 : INDIVIDUAL, 그룹 : GROUP)", example = "GROUP")
    private AccountType accountType;

    @Schema(description = "계좌 비밀번호", example = "1234")
    private String accountPassword;

    @Schema(description = "모임통장 이름(모임통장인 경우)", example = "SolTravel 모임통장")
    private String groupName;

    @Schema(description = "여행 시작일(모임통장인 경우)", example = "20240830")
    private LocalDate travelStartDate;

    @Schema(description = "여행 마감일(모임통장인 경우)", example = "20240902")
    private LocalDate travelEndDate;

    @Schema(description = "통화 코드 (예: KRW, USD)", example = "USD")
    private String currencyCode;

    @Schema(description = "아이콘 이름", example = "airPlane")
    private String iconName;

    @Schema(description = "희망 환율", example = "1333.40")
    private float exchangeRate;
}
