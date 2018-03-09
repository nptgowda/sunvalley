package com.prashanth.sunvalley.service;

import com.prashanth.sunvalley.Model.GradeDTO;
import com.prashanth.sunvalley.Model.StudentDTO;
import com.prashanth.sunvalley.domain.Grade;

import java.util.List;

public interface GradeService {
    List<GradeDTO> getAllGrades();
    GradeDTO getGradeById(Long id);
    GradeDTO createGrade(GradeDTO gradeDTO);
    GradeDTO updateGrade(Long id, GradeDTO gradeDTO);
    void deleteGrade(Long id);
    List<StudentDTO> getAllStudentsOfGrade(Long id);
}
