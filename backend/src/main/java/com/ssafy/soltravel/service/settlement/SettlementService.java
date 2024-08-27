package com.ssafy.soltravel.service.settlement;

import com.ssafy.soltravel.domain.ForeignAccount;
import com.ssafy.soltravel.domain.GeneralAccount;
import com.ssafy.soltravel.dto.settlement.SettlementRequestDto;
import com.ssafy.soltravel.dto.settlement.SettlementResponseDto;
import com.ssafy.soltravel.dto.transaction.request.ForeignTransactionRequestDto;
import com.ssafy.soltravel.dto.transaction.request.TransactionRequestDto;
import com.ssafy.soltravel.repository.ForeignAccountRepository;
import com.ssafy.soltravel.service.exchange.ExchangeService;
import com.ssafy.soltravel.service.transaction.TransactionService;
import com.ssafy.soltravel.util.LogUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SettlementService {

  private final ExchangeService exchangeService;
  private final TransactionService transactionService;
  private final ForeignAccountRepository foreignAccountRepository;

  public SettlementResponseDto executeSettlement(SettlementRequestDto requestDto) {

    ForeignAccount foreignAccount = foreignAccountRepository.findById(requestDto.getAccountId())
        .orElseThrow();

    GeneralAccount generalAccount= foreignAccount.getGeneralAccount();
    long amount = exchangeService.convertUsdToKrwWithoutFee(foreignAccount.getBalance(),
        exchangeService.getExchangeRate(foreignAccount.getCurrency().getCurrencyCode())
            .getExchangeRate());

    LogUtil.info("모임통장에 입금될 amount: ",amount);

    //외화 계좌에서 출금
    ForeignTransactionRequestDto foreignTransactionRequestDto = new ForeignTransactionRequestDto();
    foreignTransactionRequestDto.setTransactionBalance(foreignAccount.getBalance());
    foreignTransactionRequestDto.setTransactionSummary("정산 출금");
    transactionService.postForeignWithdrawal(requestDto.getAccountNo(), foreignTransactionRequestDto);


    //일반 통장에 입금
    TransactionRequestDto transactionRequestDto = new TransactionRequestDto();
    transactionRequestDto.setTransactionBalance(amount);
    transactionRequestDto.setTransactionSummary("정산 입금");
    transactionService.postAccountDeposit(generalAccount.getAccountNo(),transactionRequestDto);

    //TODO: 정산


    //TODO: 알림보내기


    SettlementResponseDto responseDto = new SettlementResponseDto();
    return responseDto;
  }
}
