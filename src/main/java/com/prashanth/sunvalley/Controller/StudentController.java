package com.prashanth.sunvalley.Controller;

import com.prashanth.sunvalley.Model.MiscFeeDTO;
import com.prashanth.sunvalley.Model.StudentDTO;
import com.prashanth.sunvalley.Model.StudentListDTO;
import com.prashanth.sunvalley.Model.StudentUpdateDTO;
import com.prashanth.sunvalley.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@Slf4j
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public StudentListDTO getAllStudents(){
        return new StudentListDTO(studentService.getAllStudents());
    }

    @GetMapping("/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO getStudentById(@PathVariable String studentId){
        return studentService.getStudentByStudentId(studentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentListDTO createStudents(@RequestBody StudentListDTO studentListDTO){
        return new StudentListDTO(studentService.createStudents(studentListDTO));
    }

    @PatchMapping("/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO updateStudent(@PathVariable String studentId, @RequestBody StudentUpdateDTO studentUpdateDTO){
        return studentService.updateStudent(studentId,studentUpdateDTO);
    }

    @DeleteMapping("/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudent(@PathVariable String studentId){
        studentService.deleteStudent(studentId);
    }

    @PostMapping("/{studentId}/miscfee")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO addMiscFeeToAllStudentsOfAGrade(@PathVariable String studentId,
                                                          @RequestBody MiscFeeDTO miscFeeDTO){
        return studentService.addMiscFeeToStudentById(studentId,miscFeeDTO);
    }

}
