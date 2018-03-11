package com.prashanth.sunvalley.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prashanth.sunvalley.Model.*;
import com.prashanth.sunvalley.domain.Fee;
import com.prashanth.sunvalley.domain.Grade;
import com.prashanth.sunvalley.domain.MiscFee;
import com.prashanth.sunvalley.domain.Student;
import com.prashanth.sunvalley.exception.NotFoundException;
import com.prashanth.sunvalley.mapper.GradeMapper;
import com.prashanth.sunvalley.mapper.MiscFeeMapper;
import com.prashanth.sunvalley.mapper.StudentMapper;
import com.prashanth.sunvalley.repository.GradeRepository;
import com.prashanth.sunvalley.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final GradeMapper gradeMapper;
    private final StudentMapper studentMapper;
    private final MiscFeeMapper miscFeeMapper;
    private final StudentService studentService;
    private final ArchiveStudentService archiveStudentService;

    public GradeServiceImpl(GradeRepository gradeRepository, GradeMapper gradeMapper,
                            StudentMapper studentMapper, MiscFeeMapper miscFeeMapper,
                            StudentService studentService,
                            ArchiveStudentService archiveStudentService) {
        this.gradeRepository = gradeRepository;
        this.gradeMapper = gradeMapper;
        this.studentMapper = studentMapper;
        this.miscFeeMapper = miscFeeMapper;
        this.studentService = studentService;
        this.archiveStudentService = archiveStudentService;
    }

    private Grade getGrade(Long id){
        Optional<Grade> gradeOptional = gradeRepository.findById(id);
        if(gradeOptional.isPresent()){
            return gradeOptional.get();
        }else{
            throw new NotFoundException("Grade with id: " + id + " not found");
        }
    }

    @Override
    public List<GradeDTO> getAllGrades() {
        return gradeRepository.findAll().stream()
                .map(gradeMapper::gradeToGradeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GradeDTO getGradeById(Long id) {
         return gradeMapper.gradeToGradeDTO(getGrade(id));
       }

    @Override
    @Transactional
    public List<GradeDTO> createGrades(GradeListDTO gradeListDTO) {
        return gradeListDTO.getGrades().stream()
                .map(this::createGrade)
                .collect(Collectors.toList());
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
        ObjectMapper objectMapper = new ObjectMapper();
        gradeRepository.deleteById(id);
    }

    @Override
    public List<StudentDTO> getAllStudentsOfGrade(Long id) {
        return getGrade(id).getStudents().stream()
                    .map(student ->
                         studentMapper.studentAndFeeToStudentDTO(student, student.getFee())
                    ).collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> addMiscFeeToAllStudentsOfGrade(Long gradeId, MiscFeeDTO miscFeeDTO) {
        Grade grade = getGrade(gradeId);
        return grade.getStudents().stream()
                .map(student ->
                    studentService.addMiscFeeToStudent(student,miscFeeDTO)
                ).collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> promoteStudents(long gradeFromId, long gradeToId, StudentPromoteListDTO studentPromoteListDTO) {
        return studentPromoteListDTO.getStudents().stream()
                .map(studentPromoteDTO ->  {
                    Student student = studentMapper.studentDTOToStudent(
                            studentService.getStudentByStudentId(studentPromoteDTO.getStudentId()));
                    //save final state before promotion

                    Student promotedStudent = studentMapper.studentDTOToStudent(
                            studentMapper.studentAndFeeToStudentDTO(student,new Fee()));
                    Fee fee = student.getFee();
                    promotedStudent.setStudentId(student.getStudentId());
                    promotedStudent.setFee(new Fee());
                    promotedStudent.getFee().setOldBalance(fee.getOldBalance().add(fee.getTuitionFee())
                            .add(fee.getBookFee())
                            .add(fee.getTransportFee()).add(fee.getUniformFee()));
                    promotedStudent.getGrade().setId(gradeToId);
                    promotedStudent.getLocation().setId(student.getLocation().getId());
                    studentService.deleteStudent(student.getStudentId().getStudentId());
                    return studentService.createStudent(
                            studentMapper.studentAndFeeToStudentDTO(
                                promotedStudent,promotedStudent.getFee()));
                }).collect(Collectors.toList());
    }
}
