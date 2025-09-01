package com.example.welcome.repository;

import com.example.welcome.TableFiveTotals;
import com.example.welcome.dto.MonthlyTotals;
import com.example.welcome.model.AfterSurgeryTableFive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AfterSurgeryTableFiveRepository extends JpaRepository<AfterSurgeryTableFive, Long> {

    // AfterSurgeryTableFiveRepository.java
    List<AfterSurgeryTableFive> findByDateBetween(LocalDate start, LocalDate end);


    Page<AfterSurgeryTableFive> findByDateBetween(LocalDate start, LocalDate end, Pageable pageable);

    @Query("""
      select new com.example.welcome.TableFiveTotals(
        coalesce(sum(t.numberOfFollowUpsForCriticallyIllPatients), 0L),
        coalesce(sum(t.numberOfCirticalRescueCases), 0L),
        coalesce(sum(t.numberOfDeaths), 0L)
      )
      from AfterSurgeryTableFive t
      where t.date between :start and :end
    """)
    TableFiveTotals computeTotalsInRange(@Param("start") LocalDate start, @Param("end") LocalDate end);

//    @Query("""
//  select new com.example.welcome.dto.MonthlyTotals(
//    YEAR(t.date),
//    MONTH(t.date),
//    coalesce(sum(t.numOfPostoperativeVisits), 0L),
//    coalesce(sum(t.numOfPostoperativeAnalgesiaCases), 0L),
//    coalesce(sum(t.numOfAdverseReactionCases), 0L),
//    coalesce(sum(t.numOfInadequateAnalgesia), 0L)
//  )
//  from AfterSurgeryTableOne t
//  where t.date between :start and :end
//  group by YEAR(t.date), MONTH(t.date)
//  order by YEAR(t.date), MONTH(t.date)
//""")
//    List<com.example.welcome.dto.MonthlyTotals> computeMonthlyTotals(
//            @Param("start") LocalDate start,
//            @Param("end") LocalDate end
//    );
}


