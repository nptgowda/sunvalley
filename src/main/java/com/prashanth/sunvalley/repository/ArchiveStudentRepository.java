package com.prashanth.sunvalley.repository;

import com.prashanth.sunvalley.domain.ArchiveStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArchiveStudentRepository extends JpaRepository<ArchiveStudent,Long> {
    Optional<ArchiveStudent> findByStudentId(String studentId);
}
