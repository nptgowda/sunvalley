package com.prashanth.sunvalley.mapper;

import com.prashanth.sunvalley.Model.GradeDTO;
import com.prashanth.sunvalley.domain.Grade;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GradeMapper {
    GradeMapper INSTANCE = Mappers.getMapper(GradeMapper.class);

    GradeDTO gradeToGradeDTO(Grade grade);
    Grade gradeDTOToGrade(GradeDTO gradeDTO);
}
