package com.example.welcome.repository;

import com.example.welcome.model.AfterSurgeryTableThree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AfterSurgeryTableThreeRepository extends JpaRepository<AfterSurgeryTableThree, Long> {
}
