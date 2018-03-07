package com.prashanth.sunvalley.mapper;

import com.prashanth.sunvalley.Model.GradeDTO;
import com.prashanth.sunvalley.domain.Grade;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
public class GradeMapperTest {
    GradeMapper gradeMapper;
    @Before
    public void setUp() throws Exception {
        gradeMapper = GradeMapper.INSTANCE;
    }

    @Test
    public void gradeToGradeDTO() throws Exception {
        Grade grade = new Grade();
        grade.setId(1L);
        grade.setSection("A");
        grade.setGrade("LKG");

        GradeDTO gradeDTO = gradeMapper.gradeToGradeDTO(grade);
        assertNotNull(gradeDTO);
        assertThat(gradeDTO.getId(),is(equalTo(grade.getId())));
        assertThat(gradeDTO.getGrade(),is(equalTo(grade.getGrade())));
        assertThat(gradeDTO.getSection(),is(equalTo(grade.getSection())));

    }
}