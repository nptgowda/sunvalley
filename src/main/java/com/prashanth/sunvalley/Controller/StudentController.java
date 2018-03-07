package com.prashanth.sunvalley.Controller;

import com.prashanth.sunvalley.Model.StudentDTO;
import com.prashanth.sunvalley.Model.StudentListDTO;
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
    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO){
        return studentService.createStudent(studentDTO);
    }

    @PutMapping("{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO updateStudent(@PathVariable String studentId, @RequestBody StudentDTO studentDTO){
        return studentService.updateStudent(studentId,studentDTO);
    }

    @DeleteMapping("{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudent(@PathVariable String studentId){
        studentService.deleteStudent(studentId);
    }
}
