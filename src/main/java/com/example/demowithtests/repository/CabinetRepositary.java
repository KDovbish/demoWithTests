package com.example.demowithtests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demowithtests.domain.Cabinet;
import org.springframework.stereotype.Repository;

@Repository
public interface CabinetRepositary extends JpaRepository<Cabinet, Integer> {
}
