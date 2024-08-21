package com.ssafy.soltravel.repository;

import com.ssafy.soltravel.domain.ForeignAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForeignAccountRepository extends JpaRepository<ForeignAccount, Long> {

}
