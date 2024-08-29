package com.ssafy.soltravel.service.account_book;

import com.ssafy.soltravel.domain.CashHistory;
import com.ssafy.soltravel.domain.ForeignAccount;
import com.ssafy.soltravel.repository.CashHistoryRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CashHistoryService {

  private final CashHistoryRepository cashHistoryRepository;

  public void saveCashHistory(CashHistory cashHistory) {
    cashHistoryRepository.save(cashHistory);
  }

  // 현금 출금
  public Double getCashFromAccount(ForeignAccount foreignAccount, Double amount) {

    // 마지막 현금 잔액 확인
    CashHistory lastHistory = cashHistoryRepository.findLastHistory().orElse(null);
    Double lastBalance = (lastHistory == null) ? 0 : lastHistory.getBalance();

    // 현금 잔액 update
    Double newBalance = lastBalance + amount;

    // 로그 저장
    CashHistory newHistory = CashHistory.createGetCashHistory(
        foreignAccount, amount, newBalance
    );

    return newBalance;
  }

}
