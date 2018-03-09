package com.prashanth.sunvalley.service;

import com.prashanth.sunvalley.Model.StudentDTO;
import com.prashanth.sunvalley.domain.*;
import com.prashanth.sunvalley.exception.NotFoundException;
import com.prashanth.sunvalley.mapper.StudentMapper;
import com.prashanth.sunvalley.repository.GradeRepository;
import com.prashanth.sunvalley.repository.LocationRepository;
import com.prashanth.sunvalley.repository.StudentIdKeeperRepository;
import com.prashanth.sunvalley.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
            .map(student ->
                 studentMapper.studentAndFeeToStudentDTO(student,student.getFee())
            ).collect(Collectors.toList());
    }

    private StudentIdKeeper getStudentIdKeeper(String studentId){
        StudentIdKeeper studentIdKeeper = new StudentIdKeeper();
        if(studentId !=null){
            Optional<StudentIdKeeper> studentIdKeeperOptional = studentIdKeeperRepository.findById(studentId);
            if(!studentIdKeeperOptional.isPresent())
                throw new NotFoundException("Student Id: " + studentId + " Not Found");
            studentIdKeeper = studentIdKeeperOptional.get();
        }
        return studentIdKeeper;
    }
    @Override
    public StudentDTO getStudentByStudentId(String studentId) {
        Optional<Student> studentOptional = studentRepository.findByStudentId(getStudentIdKeeper(studentId));
        if(studentOptional.isPresent()) {
            Student student = studentOptional.get();
            return studentMapper.studentAndFeeToStudentDTO(student, student.getFee());
        }else {
            throw new NotFoundException("Student with id: " + studentId + " Not Found");
        }
    }

    private Student studentSaveHelper(StudentDTO studentDTO,Student student){
        Optional<Grade> gradeOptional = gradeRepository.findById(studentDTO.getGrade().getId());
        if(!gradeOptional.isPresent())
            throw new NotFoundException("Grade Not Found");
        Grade grade = gradeOptional.get();
        grade.getStudents().add(student);

        student.setGrade(grade);

        student.getFee().setTuitionFee(grade.getTuitionFee());
        student.getFee().setBookFee(grade.getBookFee());
        student.getFee().setUniformFee(grade.getUniformFee());
        Optional<Location> locationOptional = locationRepository.findById(studentDTO.getLocation().getId());
        if(!locationOptional.isPresent())
            throw new NotFoundException("Location Not Found");
        Location location = locationOptional.get();
        location.getStudents().add(student);
        student.getFee().setTransportFee(location.getTransportFee());
        student.setLocation(location);
        return student;
    }

    @Override
    @Transactional
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = studentMapper.studentDTOToStudent(studentDTO);
        StudentIdKeeper studentIdKeeper = getStudentIdKeeper(null);
        studentIdKeeperRepository.save(studentIdKeeper);
        student.setStudentId(studentIdKeeper);
        student.setDateCreated(LocalDateTime.now());
        if(student.getFee() == null) {
            student.setFee(new Fee());
        }
        student.getFee().setStudent(student);
        Student savedStudent = studentRepository.save(studentSaveHelper(studentDTO,student));

        return studentMapper.studentAndFeeToStudentDTO(savedStudent,savedStudent.getFee());
    }

    @Override
    @Transactional
    public StudentDTO updateStudent(String studentId, StudentDTO studentDTO) {
        Student student = studentMapper.studentDTOToStudent(studentDTO);
        StudentIdKeeper studentIdKeeper = getStudentIdKeeper(studentId);
        student.setStudentId(studentIdKeeper);
        Optional<Student> oldStudentOptional = studentRepository.findByStudentId(studentIdKeeper);
        if(!oldStudentOptional.isPresent()){
            throw new NotFoundException("Student with id: " + studentId + " Not Found");
        }
        Student oldStudent = oldStudentOptional.get();
        student.setId(oldStudent.getId());
        student.getFee().setId(oldStudent.getFee().getId());
        student.getFee().setStudent(student);
        //todo implement update correctly
        Student savedStudent = studentRepository.save(studentSaveHelper(studentDTO,student));

        return studentMapper.studentAndFeeToStudentDTO(savedStudent,savedStudent.getFee());
    }

    @Override
    @Transactional
    public void deleteStudent(String studentId) {
        StudentIdKeeper studentIdKeeper = studentIdKeeperRepository.getOne(studentId);
        studentRepository.deleteByStudentId(studentIdKeeper);
    }
}
