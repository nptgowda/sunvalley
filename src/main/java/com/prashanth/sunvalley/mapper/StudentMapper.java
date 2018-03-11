package com.prashanth.sunvalley.mapper;

import com.prashanth.sunvalley.Model.StudentDTO;
import com.prashanth.sunvalley.domain.Fee;
import com.prashanth.sunvalley.domain.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {FeeMapper.class,GradeMapper.class,LocationMapper.class})
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

//    @Mapping(target = "studentId", source = "student.studentId.studentId")
    StudentDTO studentAndFeeToStudentDTO(Student student, Fee fee);
    Student studentDTOToStudent(StudentDTO studentDTO);


}
