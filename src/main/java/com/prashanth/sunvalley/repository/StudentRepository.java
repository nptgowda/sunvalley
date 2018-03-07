package com.prashanth.sunvalley.repository;

import com.prashanth.sunvalley.domain.Student;
import com.prashanth.sunvalley.domain.StudentIdKeeper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentId(StudentIdKeeper studentIdKeeper);
    void deleteByStudentId(StudentIdKeeper studentIdKeeper);
}
