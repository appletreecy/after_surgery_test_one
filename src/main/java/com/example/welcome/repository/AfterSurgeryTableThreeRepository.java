package com.example.welcome.repository;

import com.example.welcome.TableOneTotals;
import com.example.welcome.TableThreeTotals;
import com.example.welcome.model.AfterSurgeryTableOne;
import com.example.welcome.model.AfterSurgeryTableThree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AfterSurgeryTableThreeRepository extends JpaRepository<AfterSurgeryTableThree, Long> {

    boolean existsByDate(LocalDate date);

    @Query("select a.date from AfterSurgeryTableThree a where a.date in :dates")
    Set<LocalDate> findExistingDates(@Param("dates") Collection<LocalDate> dates);

    Optional<AfterSurgeryTableThree> findByDate(LocalDate date);

    List<AfterSurgeryTableThree> findByDateBetween(LocalDate start, LocalDate end);

    // for TableThree
    Page<AfterSurgeryTableThree> findByDateBetween(LocalDate start, LocalDate end, Pageable pageable);

    @Query("""
      select new com.example.welcome.TableThreeTotals(
        MIN(YEAR(t.date)),
        MIN(MONTH(t.date)),
        coalesce(sum(t.numOfJointComplicationCount), 0L),
        coalesce(sum(t.numOfMotorDysfunctionCount), 0L),
        coalesce(sum(t.numOfTraumaComplicationCount), 0L),
        coalesce(sum(t.numOfAnkleComplicationCount), 0L),
        coalesce(sum(t.numOfPediatricAdverseEventCount), 0L),
        coalesce(sum(t.numOfSpinalComplicationCount), 0L),
        coalesce(sum(t.numOfHandSurgeryComplicationCount), 0L),
        coalesce(sum(t.numOfObstetricAdverseEventCount), 0L),
        coalesce(sum(t.numOfGynecologicalAdverseEventCount), 0L),
        coalesce(sum(t.numOfSurgicalTreatmentCount), 0L)
      )
      from AfterSurgeryTableThree t
      where t.date between :start and :end
    """)
    TableThreeTotals computeTotalsInRange(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("""
  select new com.example.welcome.dto.MonthlyTotalsTableThree(
    YEAR(t.date),
    MONTH(t.date),
    coalesce(sum(t.numOfJointComplicationCount), 0L),
    coalesce(sum(t.numOfMotorDysfunctionCount), 0L),
    coalesce(sum(t.numOfTraumaComplicationCount), 0L),
    coalesce(sum(t.numOfAnkleComplicationCount), 0L),
    coalesce(sum(t.numOfPediatricAdverseEventCount), 0L),
    coalesce(sum(t.numOfSpinalComplicationCount), 0L),
    coalesce(sum(t.numOfHandSurgeryComplicationCount), 0L),
    coalesce(sum(t.numOfObstetricAdverseEventCount), 0L),
    coalesce(sum(t.numOfGynecologicalAdverseEventCount), 0L),
    coalesce(sum(t.numOfSurgicalTreatmentCount), 0L)
  )
  from AfterSurgeryTableThree t
  where t.date between :start and :end
  group by YEAR(t.date), MONTH(t.date)
  order by YEAR(t.date), MONTH(t.date)
""")
    List<com.example.welcome.dto.MonthlyTotalsTableThree> computeMonthlyTotals(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    // ⬇️ quarterly
    @Query("""
  select new com.example.welcome.dto.QuarterlyTotalsTableThree(
    YEAR(t.date),
    ((MONTH(t.date) - 1) / 3) + 1,
    coalesce(sum(t.numOfJointComplicationCount), 0L),
    coalesce(sum(t.numOfMotorDysfunctionCount), 0L),
    coalesce(sum(t.numOfTraumaComplicationCount), 0L),
    coalesce(sum(t.numOfAnkleComplicationCount), 0L),
    coalesce(sum(t.numOfPediatricAdverseEventCount), 0L),
    coalesce(sum(t.numOfSpinalComplicationCount), 0L),
    coalesce(sum(t.numOfHandSurgeryComplicationCount), 0L),
    coalesce(sum(t.numOfObstetricAdverseEventCount), 0L),
    coalesce(sum(t.numOfGynecologicalAdverseEventCount), 0L),
    coalesce(sum(t.numOfSurgicalTreatmentCount), 0L)
  )
  from AfterSurgeryTableThree t
  where t.date between :start and :end
  group by YEAR(t.date), ((MONTH(t.date) - 1) / 3) + 1
  order by YEAR(t.date), ((MONTH(t.date) - 1) / 3) + 1
""")
    List<com.example.welcome.dto.QuarterlyTotalsTableThree> computeQuarterlyTotals(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
}
