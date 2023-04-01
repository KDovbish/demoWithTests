package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Integer> {
    @Query(value = "with recursive PassportChain as (" +
            "select * from passports where id = ?1 and deleted = false " +
            "union all " +
            "select A.* from passports A join PassportChain B on A.id = B.prev_id" +
            ") select * from PassportChain", nativeQuery = true)
    List<Passport> findPassportChain(Integer passportId);
}
