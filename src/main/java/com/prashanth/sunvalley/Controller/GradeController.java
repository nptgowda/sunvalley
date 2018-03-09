package com.prashanth.sunvalley.Controller;

import com.prashanth.sunvalley.Model.GradeDTO;
import com.prashanth.sunvalley.Model.GradeListDTO;
import com.prashanth.sunvalley.Model.StudentDTO;
import com.prashanth.sunvalley.service.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public GradeListDTO getAllGrades(){
        return new GradeListDTO(gradeService.getAllGrades());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GradeDTO getGradeById(@PathVariable Long id){
        return gradeService.getGradeById(id);
    }

    @GetMapping("/{id}/students")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentDTO> getAllStudentsByGradeId(@PathVariable Long id){
        return gradeService.getAllStudentsOfGrade(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GradeDTO createGrade(@RequestBody GradeDTO gradeDTO){
        return gradeService.createGrade(gradeDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GradeDTO updateGrade(@PathVariable Long id, @RequestBody GradeDTO gradeDTO){
        return gradeService.updateGrade(id,gradeDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGradeById(@PathVariable Long id){
        gradeService.deleteGrade(id);
    }
}
