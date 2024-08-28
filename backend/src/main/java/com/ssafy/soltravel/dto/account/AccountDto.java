package com.ssafy.soltravel.dto.account;

import com.ssafy.soltravel.domain.Enum.AccountType;
import com.ssafy.soltravel.dto.currency.CurrencyDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class AccountDto {

    private Long id;

    private int bankCode;

    private String accountPassword;

    private String accountNo;

    private String accountName;

    private AccountType accountType;

    private CurrencyDto currency;

    private String createdAt;

    private String updatedAt;

}
