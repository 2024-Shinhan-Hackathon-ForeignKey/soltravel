package com.ssafy.soltravel.repository;

import com.ssafy.soltravel.domain.LatestRate;
import io.lettuce.core.dynamic.annotation.Param;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LatestRateRepository extends CrudRepository<LatestRate, Long> {

  @Query("SELECT lr FROM LatestRate lr WHERE lr.currency.currencyCode = :currency AND lr.postAt BETWEEN :startDt AND :endDt")
  List<LatestRate> findLatestRatesByCurrencyAndDateRange(
      @Param("currency") String currency,
      @Param("startDt") LocalDate startDt,
      @Param("endDt") LocalDate endDt
  );
}
