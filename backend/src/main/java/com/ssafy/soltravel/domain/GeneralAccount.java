package com.ssafy.soltravel.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
public class GeneralAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "general_account_id")
    private Long id;

    private Long balance;

    private int countryId;

    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "foreign_account_id")
    private ForeignAccount foreignAccount;
}
