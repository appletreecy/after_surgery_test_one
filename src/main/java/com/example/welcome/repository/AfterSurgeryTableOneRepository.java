package com.example.welcome.repository;

import com.example.welcome.TableOneTotals;
import com.example.welcome.model.AfterSurgeryTableOne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

@Repository
public interface AfterSurgeryTableOneRepository extends JpaRepository<AfterSurgeryTableOne, Long> {

    Page<AfterSurgeryTableOne> findByDateBetween(LocalDate start, LocalDate end, Pageable pageable);

    @Query("""
      select new com.example.welcome.TableOneTotals(
        coalesce(sum(t.numOfPostoperativeVisits), 0L),
        coalesce(sum(t.numOfPostoperativeAnalgesiaCases), 0L),
        coalesce(sum(t.numOfAdverseReactionCases), 0L),
        coalesce(sum(t.numOfInadequateAnalgesia), 0L)
      )
      from AfterSurgeryTableOne t
      where t.date between :start and :end
    """)
    TableOneTotals computeTotalsInRange(@Param("start") LocalDate start, @Param("end") LocalDate end);
}

