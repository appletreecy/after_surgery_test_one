// src/main/java/com/example/welcome/repository/AfterSurgeryJoinRepository.java
package com.example.welcome.repository;

import com.example.welcome.dto.AfterSurgeryJoinDto;
import com.example.welcome.model.AfterSurgeryTableOne;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface AfterSurgeryJoinRepository {
    Page<AfterSurgeryJoinDto> fetchJoinedData(LocalDate start, LocalDate end, Pageable pageable);

}
