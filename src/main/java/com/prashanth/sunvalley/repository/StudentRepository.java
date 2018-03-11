package com.prashanth.sunvalley.repository;

import com.prashanth.sunvalley.Model.StudentUpdateDTO;
import com.prashanth.sunvalley.domain.Student;
import com.prashanth.sunvalley.domain.StudentIdKeeper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentId(StudentIdKeeper studentIdKeeper);
}
