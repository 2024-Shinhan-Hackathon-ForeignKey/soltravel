package com.ssafy.soltravel.mapper;

import com.ssafy.soltravel.domain.Currency;
import com.ssafy.soltravel.domain.Enum.AccountType;
import com.ssafy.soltravel.domain.Enum.CurrencyType;
import com.ssafy.soltravel.domain.ForeignAccount;
import com.ssafy.soltravel.domain.GeneralAccount;
import com.ssafy.soltravel.dto.account.AccountDto;
import com.ssafy.soltravel.dto.account.request.CreateAccountRequestDto;
import com.ssafy.soltravel.dto.currency.CurrencyDto;
import java.util.Map;
import org.modelmapper.ModelMapper;

public class AccountMapper {

    public static AccountDto toCreateAccountDto(GeneralAccount generalAccount) {

        CurrencyDto currencyDto = new CurrencyDto("KRW", "원화");

        AccountDto accountDto = AccountDto.builder()
            .id(generalAccount.getId())
            .bankCode(generalAccount.getBankCode())
            .accountPassword(generalAccount.getAccountPassword())
            .accountName(generalAccount.getAccountName())
            .accountType(generalAccount.getAccountType())
            .accountNo(generalAccount.getAccountNo())
            .groupName(generalAccount.getGroupName())
            .currency(currencyDto)
            .createdAt(String.valueOf(generalAccount.getCreatedAt()))
            .updatedAt(String.valueOf(generalAccount.getUpdatedAt()))
            .build();

        return accountDto;
    }

    public static AccountDto toCreateAccountDto(ForeignAccount foreignAccount) {

        CurrencyDto currencyDto = new CurrencyDto(
            foreignAccount.getCurrency().getCurrencyCode(),
            foreignAccount.getCurrency().getCurrencyName()
        );

        AccountDto accountDto = AccountDto.builder()
            .id(foreignAccount.getId())
            .bankCode(foreignAccount.getBankCode())
            .accountPassword(foreignAccount.getAccountPassword())
            .accountName("신한은행 외화 모임통장")
            .accountType(AccountType.GROUP)
            .accountNo(foreignAccount.getAccountNo())
            .groupName(foreignAccount.getGroupName())
            .currency(currencyDto)
            .createdAt(String.valueOf(foreignAccount.getCreatedAt()))
            .updatedAt(String.valueOf(foreignAccount.getUpdatedAt()))
            .build();

        return accountDto;
    }

    public static ForeignAccount toForeignAccountEntitiy(
        Map<String, String> recObject,
        GeneralAccount generalAccount,
        CreateAccountRequestDto requestDto
    ) {

        CurrencyType currencyType = CurrencyType.fromCode(requestDto.getCurrencyCode());

        Currency currency = new Currency();
        currency.setCurrencyCode(currencyType.getCurrencyCode());
        currency.setCurrencyName(currencyType.getCurrencyName());

        // REC 데이터를 GeneralAccount 엔티티로 변환
        ForeignAccount foreignAccount = ForeignAccount.builder()
            .bankCode(Integer.parseInt(recObject.get("bankCode")))
            .accountNo(recObject.get("accountNo"))
            .accountPassword(requestDto.getAccountPassword())
            .balance(0.0)
            .generalAccount(generalAccount)
            .groupName(requestDto.getGroupName())
            .currency(currency)
            .build();

        return foreignAccount;
    }

}
