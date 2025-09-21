package com.example.welcome.repository;

import com.example.welcome.TableThreeTotals;
import com.example.welcome.TableTwoTotals;
import com.example.welcome.model.AfterSurgeryTableOne;
import com.example.welcome.model.AfterSurgeryTableThree;
import com.example.welcome.model.AfterSurgeryTableTwo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AfterSurgeryTableTwoRepository extends JpaRepository<AfterSurgeryTableTwo, Long> {

    List<AfterSurgeryTableTwo> findByDateBetween(LocalDate start, LocalDate end);
    // for TableTwo
    Page<AfterSurgeryTableTwo> findByDateBetween(LocalDate start, LocalDate end, Pageable pageable);

    @Query("""
      select new com.example.welcome.TableTwoTotals(
        coalesce(sum(t.numOfNauseaAndVomiting), 0L),
        coalesce(sum(t.numOfDizziness), 0L),
        coalesce(sum(t.numOfNauseaAndVomitingAndDizziness), 0L),
        coalesce(sum(t.numOfItching), 0L),
        coalesce(sum(t.numOfAllergicRash), 0L),
        coalesce(sum(t.numOfProlongedAnestheticRecovery), 0L),
        coalesce(sum(t.numOfPunctureSiteAbnormality), 0L),
        coalesce(sum(t.numOfAbdominalDistension), 0L),
        coalesce(sum(t.numOfEndotrachealIntubationDiscomfort), 0L),
        coalesce(sum(t.numOfEpigastricPain), 0L),
        coalesce(sum(t.numOfDelirium), 0L),
        coalesce(sum(t.numOfChestDiscomfort), 0L),
        coalesce(sum(t.numOfTourniquetReaction), 0L),
        coalesce(sum(t.numOfOther), 0L)
      )
      from AfterSurgeryTableTwo t
      where t.date between :start and :end
    """)
    TableTwoTotals computeTotalsInRange(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("""
  select new com.example.welcome.dto.MonthlyTotalsTableTwo(
    YEAR(t.date),
    MONTH(t.date),
    coalesce(sum(t.numOfNauseaAndVomiting), 0L),
    coalesce(sum(t.numOfDizziness), 0L),
    coalesce(sum(t.numOfNauseaAndVomitingAndDizziness), 0L),
    coalesce(sum(t.numOfItching), 0L),
    coalesce(sum(t.numOfAllergicRash), 0L),
    coalesce(sum(t.numOfProlongedAnestheticRecovery), 0L),
    coalesce(sum(t.numOfPunctureSiteAbnormality), 0L),
    coalesce(sum(t.numOfAbdominalDistension), 0L),
    coalesce(sum(t.numOfEndotrachealIntubationDiscomfort), 0L),
    coalesce(sum(t.numOfEpigastricPain), 0L),
    coalesce(sum(t.numOfDelirium), 0L),
    coalesce(sum(t.numOfChestDiscomfort), 0L),
    coalesce(sum(t.numOfTourniquetReaction), 0L),
    coalesce(sum(t.numOfOther), 0L)
  )
  from AfterSurgeryTableTwo t
  where t.date between :start and :end
  group by YEAR(t.date), MONTH(t.date)
  order by YEAR(t.date), MONTH(t.date)
""")
    List<com.example.welcome.dto.MonthlyTotalsTableTwo> computeMonthlyTotals(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    // ⬇️ quarterly
    @Query("""
  select new com.example.welcome.dto.QuarterlyTotalsTableTwo(
    YEAR(t.date),
    ((MONTH(t.date) - 1) / 3) + 1,
    coalesce(sum(t.numOfNauseaAndVomiting), 0L),
    coalesce(sum(t.numOfDizziness), 0L),
    coalesce(sum(t.numOfNauseaAndVomitingAndDizziness), 0L),
    coalesce(sum(t.numOfItching), 0L),
    coalesce(sum(t.numOfAllergicRash), 0L),
    coalesce(sum(t.numOfProlongedAnestheticRecovery), 0L),
    coalesce(sum(t.numOfPunctureSiteAbnormality), 0L),
    coalesce(sum(t.numOfAbdominalDistension), 0L),
    coalesce(sum(t.numOfEndotrachealIntubationDiscomfort), 0L),
    coalesce(sum(t.numOfEpigastricPain), 0L),
    coalesce(sum(t.numOfDelirium), 0L),
    coalesce(sum(t.numOfChestDiscomfort), 0L),
    coalesce(sum(t.numOfTourniquetReaction), 0L),
    coalesce(sum(t.numOfOther), 0L)
  )
  from AfterSurgeryTableTwo t
  where t.date between :start and :end
  group by YEAR(t.date), ((MONTH(t.date) - 1) / 3) + 1
  order by YEAR(t.date), ((MONTH(t.date) - 1) / 3) + 1
""")
    List<com.example.welcome.dto.QuarterlyTotalsTableTwo> computeQuarterlyTotals(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
}


