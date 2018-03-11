package com.prashanth.sunvalley.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prashanth.sunvalley.Model.MiscFeeDTO;
import com.prashanth.sunvalley.Model.StudentDTO;
import com.prashanth.sunvalley.Model.StudentListDTO;
import com.prashanth.sunvalley.Model.StudentUpdateDTO;
import com.prashanth.sunvalley.domain.*;
import com.prashanth.sunvalley.exception.NotFoundException;
import com.prashanth.sunvalley.mapper.MiscFeeMapper;
import com.prashanth.sunvalley.mapper.StudentMapper;
import com.prashanth.sunvalley.repository.GradeRepository;
import com.prashanth.sunvalley.repository.LocationRepository;
import com.prashanth.sunvalley.repository.StudentIdKeeperRepository;
import com.prashanth.sunvalley.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
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
    private final MiscFeeMapper miscFeeMapper;
    private final ArchiveStudentService archiveStudentService;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper,
                              StudentIdKeeperRepository studentIdKeeperRepository, GradeRepository gradeRepository,
                              LocationRepository locationRepository, MiscFeeMapper miscFeeMapper,
                              ArchiveStudentService archiveStudentService) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.studentIdKeeperRepository = studentIdKeeperRepository;
        this.gradeRepository = gradeRepository;
        this.locationRepository = locationRepository;
        this.miscFeeMapper = miscFeeMapper;
        this.archiveStudentService = archiveStudentService;
    }

    @Override
    public List<StudentDTO> getAllStudents() {
    return studentRepository.findAll().stream()
            .map(student ->
                 studentMapper.studentAndFeeToStudentDTO(student,student.getFee())
            ).collect(Collectors.toList());
    }

    private Student getStudent(String studentId){
        StudentIdKeeper studentIdKeeper = new StudentIdKeeper();
        if(studentId !=null){
            Optional<StudentIdKeeper> studentIdKeeperOptional = studentIdKeeperRepository.findById(studentId);
            if(!studentIdKeeperOptional.isPresent())
                throw new NotFoundException("Student Id: " + studentId + " Not Found");
            studentIdKeeper = studentIdKeeperOptional.get();
        }
        Optional<Student> studentOptional = studentRepository.findByStudentId(studentIdKeeper);
        if(!studentOptional.isPresent()) {
            throw new NotFoundException("Student with Id: " + studentId + " Not Found");
        }
        return studentOptional.get();
    }

    @Override
    public StudentDTO getStudentByStudentId(String studentId) {
            Student student = getStudent(studentId);
            return studentMapper.studentAndFeeToStudentDTO(student, student.getFee());
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
    public List<StudentDTO> createStudents(StudentListDTO studentListDTO) {
        return studentListDTO.getStudents().stream()
                .map(this::createStudent)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = studentMapper.studentDTOToStudent(studentDTO);
        if(studentDTO.getStudentId() == null) {
            StudentIdKeeper studentIdKeeper = new StudentIdKeeper();
            studentIdKeeperRepository.save(studentIdKeeper);
            student.setStudentId(studentIdKeeper);
            student.setDateCreated(LocalDateTime.now());

        }
        student.setDateUpdated(LocalDateTime.now());
        if(student.getFee() == null) {
            student.setFee(new Fee());
        }
        student.getFee().setStudent(student);
        Student savedStudent = studentRepository.save(studentSaveHelper(studentDTO,student));

        return studentMapper.studentAndFeeToStudentDTO(savedStudent,savedStudent.getFee());
    }

    @Override
    @Transactional
    public StudentDTO updateStudent(String studentId, StudentUpdateDTO studentUpdateDTO) {

        Student student = getStudent(studentId);

        if(studentUpdateDTO.getFirstName() != null)
            student.setFirstName(studentUpdateDTO.getFirstName());
        if(studentUpdateDTO.getLastName() != null)
            student.setLastName(studentUpdateDTO.getLastName());
        if(studentUpdateDTO.getInitial() != null)
            student.setInitial(studentUpdateDTO.getInitial());
        if(studentUpdateDTO.getDateOfBirth() != null)
            student.setDateOfBirth(studentUpdateDTO.getDateOfBirth());
        if(studentUpdateDTO.getEmail() != null)
            student.setEmail(studentUpdateDTO.getEmail());
        if(studentUpdateDTO.getFathersName() != null)
            student.setFathersName(studentUpdateDTO.getFathersName());
        if(studentUpdateDTO.getFathersNumber() != null)
            student.setFathersNumber(studentUpdateDTO.getFathersNumber());
        if(studentUpdateDTO.getMothersName() != null)
            student.setMothersName(studentUpdateDTO.getMothersName());
        if(studentUpdateDTO.getMothersNumber() != null)
            student.setMothersNumber(studentUpdateDTO.getMothersNumber());
        student.setDateUpdated(LocalDateTime.now());
        return studentMapper.studentAndFeeToStudentDTO(student,student.getFee());
    }


    @Override
    @Transactional
    public void deleteStudent(String studentId) {
        Student student = getStudent(studentId);
        if(archiveStudentService.archiveStudent(student))
             studentRepository.delete(student);
        else
            throw new RuntimeException("Student archive failed. Try deleting again");
    }
    @Override
    public StudentDTO addMiscFeeToStudentById(String studentId, MiscFeeDTO miscFeeDTO) {
        Student student = getStudent(studentId);

        return addMiscFeeToStudent(student,miscFeeDTO);
    }

    public StudentDTO addMiscFeeToStudent(Student student, MiscFeeDTO miscFeeDTO){
        MiscFee miscFee = miscFeeMapper.miscFeeDTOToMiscFee(miscFeeDTO);
        miscFee.setFee(student.getFee());
        student.getFee().getMiscFee().add(miscFee);
        student.getFee().getTotalMiscFeeAmount().add(miscFee.getFeeAmount());
        return studentMapper.studentAndFeeToStudentDTO(studentRepository.save(student),student.getFee());
    }

}
