package com.ssafy.soltravel.repository;

import com.ssafy.soltravel.domain.CashHistory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CashHistoryRepository {

  private final EntityManager em;

  public void save(CashHistory cashHistory) {
    em.persist(cashHistory);
  }

  public Optional<List<CashHistory>> findAll() {
    List<CashHistory> result = em.createQuery("select c from CashHistory c", CashHistory.class)
        .getResultList();
    return Optional.ofNullable(result);
  }

  public Optional<CashHistory> findById(Long id) {
    return Optional.ofNullable(em.find(CashHistory.class, id));
  }

  public Optional<CashHistory> findLastHistory() {
    List<CashHistory> result = em.createQuery("select c from CashHistory c order by c.id desc limit 1", CashHistory.class)
        .getResultList();
    return (result.isEmpty()) ? Optional.empty() : Optional.of(result.get(0));
  }

}
