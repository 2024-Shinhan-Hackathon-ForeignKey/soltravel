package com.ssafy.soltravel.repository;

import com.ssafy.soltravel.domain.GeneralAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GeneralAccountRepository extends JpaRepository<GeneralAccount, Long> {

    @Transactional
    void deleteByAccountNo(String accountNo);

//    @Query("SELECT u FROM Participant p " +
//        "JOIN p.generalAccount ga " +
//        "JOIN p.user u " +
//        "WHERE ga.accountNo = :accountNo")
//    User findUserByAccountNo(@Param("accountNo") String accountNo);

    Optional<GeneralAccount> findByAccountNo(String accountNo);

}
