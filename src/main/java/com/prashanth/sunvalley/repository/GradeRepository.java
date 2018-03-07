package com.prashanth.sunvalley.repository;

import com.prashanth.sunvalley.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade,Long> {
    Optional<Grade> findByGradeAndSection(String grade, String section);
}
