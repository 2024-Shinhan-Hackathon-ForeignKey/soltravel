package com.ssafy.soltravel.repository;

import com.ssafy.soltravel.domain.GeneralAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GeneralAccountRepository extends JpaRepository<GeneralAccount, Long> {

    @Transactional
    void deleteByAccountNo(String accountNo);

    GeneralAccount findByAccountNo(String accountNo);

    @Query("SELECT ga.balance FROM GeneralAccount ga WHERE ga.id = :accountId")
    Long findBalanceByAccountId(@Param("accountId") Long accountId);
}
