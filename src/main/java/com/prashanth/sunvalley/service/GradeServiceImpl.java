package com.prashanth.sunvalley.service;

import com.prashanth.sunvalley.Model.GradeDTO;
import com.prashanth.sunvalley.Model.StudentDTO;
import com.prashanth.sunvalley.domain.Grade;
import com.prashanth.sunvalley.domain.Student;
import com.prashanth.sunvalley.exception.NotFoundException;
import com.prashanth.sunvalley.mapper.GradeMapper;
import com.prashanth.sunvalley.mapper.StudentMapper;
import com.prashanth.sunvalley.repository.GradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final GradeMapper gradeMapper;
    private final StudentMapper studentMapper;

    public GradeServiceImpl(GradeRepository gradeRepository, GradeMapper gradeMapper, StudentMapper studentMapper) {
        this.gradeRepository = gradeRepository;
        this.gradeMapper = gradeMapper;
        this.studentMapper = studentMapper;
    }

    @Override
    public List<GradeDTO> getAllGrades() {
        return gradeRepository.findAll().stream()
                .map(gradeMapper::gradeToGradeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GradeDTO getGradeById(Long id) {
        Optional<Grade> gradeOptional = gradeRepository.findById(id);
        if(gradeOptional.isPresent()){
            return gradeMapper.gradeToGradeDTO(gradeOptional.get());
        }else {
            throw new NotFoundException("Grade with id: " + id + " not found");
        }
    }

    @Override
    public GradeDTO createGrade(GradeDTO gradeDTO) {
        Grade grade = gradeMapper.gradeDTOToGrade(gradeDTO);
        return gradeMapper.gradeToGradeDTO(gradeRepository.save(grade));
    }

    @Override
    public GradeDTO updateGrade(Long id, GradeDTO gradeDTO) {
        Grade grade = gradeMapper.gradeDTOToGrade(gradeDTO);
        grade.setId(id);
        return gradeMapper.gradeToGradeDTO(gradeRepository.save(grade));
    }

    @Override
    @Transactional
    public void deleteGrade(Long id) {
        gradeRepository.deleteById(id);
    }

    @Override
    public List<StudentDTO> getAllStudentsOfGrade(Long id) {
        Optional<Grade> gradeOptional = gradeRepository.findById(id);
        if(gradeOptional.isPresent()) {
            return gradeOptional.get().getStudents().stream()
                    .map(student ->
                         studentMapper.studentAndFeeToStudentDTO(student, student.getFee())
                    ).collect(Collectors.toList());
        }else {
            throw new NotFoundException("Grade with id: " + id + " not found");
        }
    }
}
