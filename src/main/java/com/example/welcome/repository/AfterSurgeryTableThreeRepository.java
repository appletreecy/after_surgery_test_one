package com.example.welcome.repository;

import com.example.welcome.model.AfterSurgeryTableThree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AfterSurgeryTableThreeRepository extends JpaRepository<AfterSurgeryTableThree, Long> {

    boolean existsByDate(LocalDate date);

    @Query("select a.date from AfterSurgeryTableThree a where a.date in :dates")
    Set<LocalDate> findExistingDates(@Param("dates") Collection<LocalDate> dates);

    Optional<AfterSurgeryTableThree> findByDate(LocalDate date);
}
