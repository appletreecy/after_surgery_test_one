package com.example.welcome.repository;

import com.example.welcome.TableFiveTotals;
import com.example.welcome.model.AfterSurgeryTableFive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface AfterSurgeryTableFiveRepository extends JpaRepository<AfterSurgeryTableFive, Long> {

    @Query("select a.date from AfterSurgeryTableFive a where a.date in :dates")
    Set<LocalDate> findExistingDates(@Param("dates") Collection<LocalDate> dates);

    // AfterSurgeryTableFiveRepository.java
    List<AfterSurgeryTableFive> findByDateBetween(LocalDate start, LocalDate end);


    Page<AfterSurgeryTableFive> findByDateBetween(LocalDate start, LocalDate end, Pageable pageable);

    @Query("""
      select new com.example.welcome.TableFiveTotals(
        coalesce(sum(t.numberOfFollowUpsForCriticallyIllPatients), 0L),
        coalesce(sum(t.numberOfCriticalRescueCases), 0L),
        coalesce(sum(t.numberOfDeaths), 0L)
      )
      from AfterSurgeryTableFive t
      where t.date between :start and :end
    """)
    TableFiveTotals computeTotalsInRange(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("""
  select new com.example.welcome.dto.MonthlyTotalsTableFive(
    YEAR(t.date),
    MONTH(t.date),
    coalesce(sum(t.numberOfFollowUpsForCriticallyIllPatients), 0L),
    coalesce(sum(t.numberOfCriticalRescueCases), 0L),
    coalesce(sum(t.numberOfDeaths), 0L)
  )
  from AfterSurgeryTableFive t
  where t.date between :start and :end
  group by YEAR(t.date), MONTH(t.date)
  order by YEAR(t.date), MONTH(t.date)
""")
    List<com.example.welcome.dto.MonthlyTotalsTableFive> computeMonthlyTotals(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
}


