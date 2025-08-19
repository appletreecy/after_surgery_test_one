package com.example.welcome.repository;

import com.example.welcome.model.AfterSurgeryTableOne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AfterSurgeryTableOneRepository extends JpaRepository<AfterSurgeryTableOne, Long> {
}

