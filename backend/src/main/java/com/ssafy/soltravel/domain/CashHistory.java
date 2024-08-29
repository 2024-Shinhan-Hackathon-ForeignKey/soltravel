package com.ssafy.soltravel.domain;

import com.ssafy.soltravel.domain.Enum.CashTransactionType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@Entity
@Table(name = "cash_history")
public class CashHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cash_history_id;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "foreign_account_id")
  private ForeignAccount foreignAccount;

  @Column
  private Double amount;

  @Enumerated(EnumType.STRING)
  @Column(name = "transaction_type")
  private CashTransactionType transactionType;

  @Column(name = "transaction_at")
  private LocalDateTime transactionAt;

  @Column(name = "balance")
  private Double balance;

  @Column(name = "store")
  private String store;


  /*
  * 생성 메서드
  */

  public void setForeignAccount(ForeignAccount foreignAccount) {
    this.foreignAccount = foreignAccount;
  }

  public static CashHistory createGetCashHistory(
      ForeignAccount foreignAccount, Double amount, Double balance
  ) {
    CashHistory cashHistory = new CashHistory();
    foreignAccount.addCashHistory(cashHistory);
    cashHistory.amount = amount;
    cashHistory.balance = balance;
    cashHistory.store = "";
    cashHistory.transactionType = CashTransactionType.G;
    cashHistory.transactionAt = LocalDateTime.now();


    return new CashHistory();
  }
}
