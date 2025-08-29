package com.example.welcome;

import com.example.welcome.model.AfterSurgeryTableOne;
import com.example.welcome.repository.AfterSurgeryTableOneRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class AfterSurgeryTableOneRepositoryTests {

    @Autowired
    private AfterSurgeryTableOneRepository repo;

    @Test
    void computeTotalsInRange_shouldSumFieldsBetweenDates() {
        // given
        AfterSurgeryTableOne a = new AfterSurgeryTableOne();
        a.setDate(LocalDate.of(2025, 8, 1));
        a.setNumOfPostoperativeVisits(10);
        a.setNumOfPostoperativeAnalgesiaCases(3);
        a.setNumOfAdverseReactionCases(2);
        a.setNumOfInadequateAnalgesia(1);
        repo.save(a);

        AfterSurgeryTableOne b = new AfterSurgeryTableOne();
        b.setDate(LocalDate.of(2025, 8, 2));
        b.setNumOfPostoperativeVisits(5);
        b.setNumOfPostoperativeAnalgesiaCases(4);
        b.setNumOfAdverseReactionCases(0);
        b.setNumOfInadequateAnalgesia(2);
        repo.save(b);

        // when
        TableOneTotals totals = repo.computeTotalsInRange(
                LocalDate.of(2025, 8, 1),
                LocalDate.of(2025, 8, 31));

        // then
        assertThat(totals).isNotNull();
        assertThat(totals.totalVisits()).isEqualTo(15L);
        assertThat(totals.totalAnalgesia()).isEqualTo(7L);
        assertThat(totals.totalAdverse()).isEqualTo(2L);
        assertThat(totals.totalInadequate()).isEqualTo(3L);
    }
}

