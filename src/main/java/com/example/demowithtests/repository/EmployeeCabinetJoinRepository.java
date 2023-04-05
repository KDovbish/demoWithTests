package com.example.demowithtests.repository;

import com.example.demowithtests.domain.EmployeeCabinetJoinEntity;
import com.example.demowithtests.domain.EmployeeCabinetJoinPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeCabinetJoinRepository extends JpaRepository<EmployeeCabinetJoinEntity, EmployeeCabinetJoinPrimaryKey> {
}
