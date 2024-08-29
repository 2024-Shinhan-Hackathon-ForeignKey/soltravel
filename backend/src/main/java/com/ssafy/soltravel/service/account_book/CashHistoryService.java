package com.ssafy.soltravel.service.account_book;

import com.ssafy.soltravel.domain.CashHistory;
import com.ssafy.soltravel.domain.ForeignAccount;
import com.ssafy.soltravel.exception.LackOfBalanceException;
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

    // 변동된 금액 적용한 history 엔티티 생성
    CashHistory newHistory = CashHistory.createGetCashHistory(
        foreignAccount, amount, newBalance
    );

    // 저장
    cashHistoryRepository.save(newHistory);
    return newBalance;
  }

  // 현금 사용
  public Double payCash(ForeignAccount foreignAccount, Double amount)
    throws LackOfBalanceException {

    // 마지막 현금 잔액 확인
    CashHistory lastHistory = cashHistoryRepository.findLastHistory().orElse(null);
    Double lastBalance = (lastHistory == null) ? 0 : lastHistory.getBalance();

    // 현금 잔액 update
    Double newBalance = lastBalance - amount;
    if(newBalance < 0) {
      throw new LackOfBalanceException(lastBalance, amount);
    }

    // 변동된 금액 적용한 history 엔티티 생성
    CashHistory newHistory = CashHistory.createGetCashHistory(
        foreignAccount, amount, newBalance
    );

    // 저장
    cashHistoryRepository.save(newHistory);
    return newBalance;
  }

}
