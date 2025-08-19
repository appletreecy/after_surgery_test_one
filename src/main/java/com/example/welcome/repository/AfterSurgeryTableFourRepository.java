package com.example.welcome.repository;

import com.example.welcome.model.AfterSurgeryTableFour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AfterSurgeryTableFourRepository extends JpaRepository<AfterSurgeryTableFour, Long> {
}
