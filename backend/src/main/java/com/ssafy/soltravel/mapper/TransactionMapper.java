package com.ssafy.soltravel.mapper;

import com.ssafy.soltravel.dto.account_book.AccountHistoryReadRequestDto;
import com.ssafy.soltravel.dto.transaction.request.TransactionHistoryRequestDto;

public class TransactionMapper {

  public static TransactionHistoryRequestDto convertaccountToTransaction(
      AccountHistoryReadRequestDto accountDto
  ) {
    return TransactionHistoryRequestDto.builder()
        .startDate(accountDto.getStartDate())
        .endDate(accountDto.getEndDate())
        .transactionType(accountDto.getTransactionType())
        .orderByType(accountDto.getOrderByType())
        .build();
  }

}
