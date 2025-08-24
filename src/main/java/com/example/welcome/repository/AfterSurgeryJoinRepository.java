// src/main/java/com/example/welcome/repository/AfterSurgeryJoinRepository.java
package com.example.welcome.repository;

import com.example.welcome.dto.AfterSurgeryJoinDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface AfterSurgeryJoinRepository {
    Page<AfterSurgeryJoinDto> fetchJoinedData(LocalDate start, LocalDate end, Pageable pageable);
}
