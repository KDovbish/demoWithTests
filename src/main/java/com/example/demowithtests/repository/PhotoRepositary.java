package com.example.demowithtests.repository;

import com.example.demowithtests.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepositary extends JpaRepository<Photo, Integer> {
}
