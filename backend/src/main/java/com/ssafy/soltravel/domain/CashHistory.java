package com.ssafy.soltravel.domain;

import com.ssafy.soltravel.domain.Enum.CashTransactionType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

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

  @Column(name = "transaction_type")
  private CashTransactionType transactionType;

  @Column(name = "transaction_at")
  private LocalDateTime transactionAt;

  @Column(name = "balance")
  private Double balance;

  @Column(name = "store")
  private String store;
}
