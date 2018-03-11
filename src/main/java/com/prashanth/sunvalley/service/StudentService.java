package com.prashanth.sunvalley.service;

import com.prashanth.sunvalley.Model.MiscFeeDTO;
import com.prashanth.sunvalley.Model.StudentDTO;
import com.prashanth.sunvalley.Model.StudentListDTO;
import com.prashanth.sunvalley.Model.StudentUpdateDTO;
import com.prashanth.sunvalley.domain.Student;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    List<StudentDTO> getAllStudents();

    StudentDTO getStudentByStudentId(String studentId);

    List<StudentDTO> createStudents(StudentListDTO studentListDTO);

    StudentDTO createStudent(StudentDTO studentDTO);

    StudentDTO updateStudent(String studentId, StudentUpdateDTO studentUpdateDTO);

    void deleteStudent(String studentId);

    StudentDTO addMiscFeeToStudentById(String studentId, MiscFeeDTO miscFeeDTO);
    StudentDTO addMiscFeeToStudent(Student student, MiscFeeDTO miscFeeDTO);
}
