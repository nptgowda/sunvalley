package com.prashanth.sunvalley.service;

import com.prashanth.sunvalley.Model.StudentDTO;
import com.prashanth.sunvalley.domain.Student;

import java.util.List;

public interface StudentService {
    List<StudentDTO> getAllStudents();
    StudentDTO getStudentByStudentId(String studentId);
    StudentDTO createStudent(StudentDTO studentDTO);
    StudentDTO updateStudent(String studentId, StudentDTO studentDTO);
    void deleteStudent(String studentId);
}
