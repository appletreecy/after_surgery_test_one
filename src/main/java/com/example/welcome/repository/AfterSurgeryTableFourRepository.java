package com.example.welcome.repository;

import com.example.welcome.TableFourTotals;
import com.example.welcome.TableThreeTotals;
import com.example.welcome.model.AfterSurgeryTableFour;
import com.example.welcome.model.AfterSurgeryTableThree;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AfterSurgeryTableFourRepository extends JpaRepository<AfterSurgeryTableFour, Long> {

    boolean existsByDate(LocalDate date);

    @Query("select a.date from AfterSurgeryTableFour a where a.date in :dates")
    Set<LocalDate> findExistingDates(@Param("dates") Collection<LocalDate> dates);

    Optional<AfterSurgeryTableThree> findByDate(LocalDate date);

    // for TableFour
    Page<AfterSurgeryTableFour> findByDateBetween(LocalDate start, LocalDate end, Pageable pageable);

    @Query("""
      select new com.example.welcome.TableFourTotals(
        coalesce(sum(t.numOfFormulationOne), 0L),
        coalesce(sum(t.numOfFormulationTwo), 0L),
        coalesce(sum(t.numOfFormulationThree), 0L),
        coalesce(sum(t.numOfFormulationFour), 0L),
        coalesce(sum(t.numOfFormulationFive), 0L),
        coalesce(sum(t.numOfFormulationSix), 0L)
      )
      from AfterSurgeryTableFour t
      where t.date between :start and :end
    """)
    TableFourTotals computeTotalsInRange(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
