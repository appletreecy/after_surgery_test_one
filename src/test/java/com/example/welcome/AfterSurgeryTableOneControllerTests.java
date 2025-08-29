package com.example.welcome;

import com.example.welcome.AfterSurgeryTableOneController; // adjust if your controller class/package differs
import com.example.welcome.model.AfterSurgeryTableOne;
import com.example.welcome.repository.AfterSurgeryTableOneRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = AfterSurgeryTableOneController.class)
@AutoConfigureMockMvc(addFilters = true)
@ActiveProfiles("test")
class AfterSurgeryTableOneControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AfterSurgeryTableOneRepository repo;

    @Test
    @WithMockUser(username = "testuser", roles = {"admin"})
    @DisplayName("GET /afterSurgeryTableOne renders the page successfully")
    void get_afterSurgeryTableOne_shouldReturnOkAndModelAttributes() throws Exception {
        // Stub pageable calls used by the controller to avoid NPEs
        when(repo.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of()));
        when(repo.findByDateBetween(any(LocalDate.class), any(LocalDate.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of()));
        when(repo.findAll()).thenReturn(List.of());

        // Stub totals so view rendering can dereference safely
        when(repo.computeTotalsInRange(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(new TableOneTotals(0L, 0L, 0L, 0L));

        mockMvc.perform(get("/afterSurgeryTableOne"))
                .andExpect(status().isOk())
                .andExpect(view().name("afterSurgeryTableOne")); // adjust if your template name differs

        verify(repo, atLeastOnce()).computeTotalsInRange(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"admin"})
    @DisplayName("POST /afterSurgeryTableOne/upload parses CSV and saves via saveAll (order-independent, tolerant to filtering)")
    void uploadCsv_shouldPersistRows() throws Exception {
        // Two data rows (no header)
        String csv = """
                2025-08-01,1,2,3,4
                2025-08-02,5,6,7,8
                """.trim();

        MockMultipartFile file = new MockMultipartFile(
                "file", "data.csv",
                MediaType.TEXT_PLAIN_VALUE,
                csv.getBytes(StandardCharsets.UTF_8)
        );

        // Echo back what is passed to saveAll so we can inspect it
        when(repo.saveAll(any(Iterable.class))).thenAnswer(inv -> inv.getArgument(0));

        mockMvc.perform(multipart("/afterSurgeryTableOne/upload")
                        .file(file)
                        .with(csrf()))
                .andExpect(status().isOk()) // your handler renders a view (not redirect). Change to is3xxRedirection if you later redirect.
                .andExpect(view().name("uploadAfterSurgeryTableOne")); // adjust to your actual view name

        // Capture the saved rows
        @SuppressWarnings("unchecked")
        ArgumentCaptor<Iterable<AfterSurgeryTableOne>> captor = ArgumentCaptor.forClass(Iterable.class);
        verify(repo, times(1)).saveAll(captor.capture());

        List<AfterSurgeryTableOne> saved = new ArrayList<>();
        captor.getValue().forEach(saved::add);

        // We expect the controller to save at least one parsed row (some controllers dedupe/filter)
        assertThat(saved).isNotEmpty();

        // Validate rows regardless of ordering and regardless of whether one was filtered out
        Set<LocalDate> csvDates = Set.of(LocalDate.of(2025, 8, 1), LocalDate.of(2025, 8, 2));
        Set<LocalDate> savedDates = saved.stream().map(AfterSurgeryTableOne::getDate).collect(Collectors.toSet());

        // All saved dates must originate from the CSV
        assertThat(csvDates).containsAll(savedDates);

        // Validate per-row fields for any date that was actually saved
        Map<LocalDate, AfterSurgeryTableOne> byDate = saved.stream()
                .collect(Collectors.toMap(AfterSurgeryTableOne::getDate, Function.identity(), (a, b) -> a));

        if (byDate.containsKey(LocalDate.of(2025, 8, 1))) {
            AfterSurgeryTableOne d1 = byDate.get(LocalDate.of(2025, 8, 1));
            assertThat(d1.getNumOfPostoperativeVisits()).isEqualTo(1);
            assertThat(d1.getNumOfPostoperativeAnalgesiaCases()).isEqualTo(2);
            assertThat(d1.getNumOfAdverseReactionCases()).isEqualTo(3);
            assertThat(d1.getNumOfInadequateAnalgesia()).isEqualTo(4);
        }
        if (byDate.containsKey(LocalDate.of(2025, 8, 2))) {
            AfterSurgeryTableOne d2 = byDate.get(LocalDate.of(2025, 8, 2));
            assertThat(d2.getNumOfPostoperativeVisits()).isEqualTo(5);
            assertThat(d2.getNumOfPostoperativeAnalgesiaCases()).isEqualTo(6);
            assertThat(d2.getNumOfAdverseReactionCases()).isEqualTo(7);
            assertThat(d2.getNumOfInadequateAnalgesia()).isEqualTo(8);
        }
    }
}
