package com.prashanth.sunvalley.service;

import com.prashanth.sunvalley.Model.StudentDTO;
import com.prashanth.sunvalley.domain.*;
import com.prashanth.sunvalley.mapper.StudentMapper;
import com.prashanth.sunvalley.repository.GradeRepository;
import com.prashanth.sunvalley.repository.LocationRepository;
import com.prashanth.sunvalley.repository.StudentIdKeeperRepository;
import com.prashanth.sunvalley.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final StudentIdKeeperRepository studentIdKeeperRepository;
    private final GradeRepository gradeRepository;
    private final LocationRepository locationRepository;


    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper,
                              StudentIdKeeperRepository studentIdKeeperRepository,
                              GradeRepository gradeRepository, LocationRepository locationRepository) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.studentIdKeeperRepository = studentIdKeeperRepository;
        this.gradeRepository = gradeRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<StudentDTO> getAllStudents() {
    return studentRepository.findAll().stream()
            .map(student -> {
                   StudentDTO studentDTO = studentMapper.studentAndFeeToStudentDTO(student,student.getFee());
                   return studentDTO;
            }).collect(Collectors.toList());
    }

    private StudentIdKeeper getStudentIdKeeper(String studentId){
        StudentIdKeeper studentIdKeeper = new StudentIdKeeper();
        if(studentId !=null){
            studentIdKeeper = studentIdKeeperRepository.getOne(studentId);
        }
        return studentIdKeeper;
    }
    @Override
    public StudentDTO getStudentByStudentId(String studentId) {
        Student student = studentRepository.findByStudentId(getStudentIdKeeper(studentId)).get();
        return studentMapper.studentAndFeeToStudentDTO(student,student.getFee());
    }

    private Student studentSaveHelper(StudentDTO studentDTO,Student student){
        Grade grade = gradeRepository.findById(studentDTO.getGrade().getId()).get();
        grade.getStudents().add(student);
        student.setGrade(grade);
        Location location = locationRepository.findById(studentDTO.getLocation().getId()).get();
        location.getStudent().add(student);
        student.setLocation(location);
        return student;
    }

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = studentMapper.studentDTOToStudent(studentDTO);
        StudentIdKeeper studentIdKeeper = getStudentIdKeeper(null);
        studentIdKeeperRepository.save(studentIdKeeper);
        student.setStudentId(studentIdKeeper);
        student.setDateCreated(LocalDateTime.now());
        student.getFee().setStudent(student);

        Student savedStudent = studentRepository.save(studentSaveHelper(studentDTO,student));

        return studentMapper.studentAndFeeToStudentDTO(savedStudent,savedStudent.getFee());
    }

    @Override
    public StudentDTO updateStudent(String studentId, StudentDTO studentDTO) {
        Student student = studentMapper.studentDTOToStudent(studentDTO);
        StudentIdKeeper studentIdKeeper = getStudentIdKeeper(studentId);
        student.setStudentId(studentIdKeeper);
        Student oldStudent = studentRepository.findByStudentId(studentIdKeeper).get();
        student.setId(oldStudent.getId());
        student.getFee().setId(oldStudent.getFee().getId());
        student.getFee().setStudent(student);

        Student savedStudent = studentRepository.save(studentSaveHelper(studentDTO,student));

        return studentMapper.studentAndFeeToStudentDTO(savedStudent,savedStudent.getFee());
    }

    @Override
    public void deleteStudent(String studentId) {
        StudentIdKeeper studentIdKeeper = studentIdKeeperRepository.getOne(studentId);
        studentRepository.deleteByStudentId(studentIdKeeper);
    }
}
