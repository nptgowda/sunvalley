package com.prashanth.sunvalley.service;

import com.prashanth.sunvalley.Model.*;

import java.util.List;

public interface GradeService {
    List<GradeDTO> getAllGrades();
    GradeDTO getGradeById(Long id);
    List<GradeDTO> createGrades(GradeListDTO gradeListDTO);
    GradeDTO createGrade(GradeDTO gradeDTO);
    GradeDTO updateGrade(Long id, GradeDTO gradeDTO);
    void deleteGrade(Long id);
    List<StudentDTO> getAllStudentsOfGrade(Long id);

    List<StudentDTO> addMiscFeeToAllStudentsOfGrade(Long gradeId, MiscFeeDTO miscFeeDTO);

    List<StudentDTO> promoteStudents(long gradeFromId, long gradeToId, StudentPromoteListDTO studentPromoteListDTO);
}
