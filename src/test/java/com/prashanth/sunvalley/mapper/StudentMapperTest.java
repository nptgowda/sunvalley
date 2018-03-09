package com.prashanth.sunvalley.mapper;

import com.prashanth.sunvalley.Model.FeeDTO;
import com.prashanth.sunvalley.Model.StudentDTO;
import com.prashanth.sunvalley.domain.Fee;
import com.prashanth.sunvalley.domain.Student;
import com.prashanth.sunvalley.domain.StudentIdKeeper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Ignore
public class StudentMapperTest {

    private StudentMapper studentMapper;

    @Mock
    private
    FeeMapper feeMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        studentMapper = StudentMapper.INSTANCE;
    }

    @Test
    public void studentAndFeeToStudentDTO() {
        Student student = new Student();
        student.setFirstName("Prashanth");
        StudentIdKeeper studentIdKeeper = new StudentIdKeeper();
        studentIdKeeper.setStudentId("SV1001");
        student.setStudentId(studentIdKeeper);

        Fee fee = new Fee();
        fee.setId(1L);
        fee.setBookFee(new BigDecimal(3500));
        fee.setTuitionFee(new BigDecimal(15000));
        student.setFee(fee);

        FeeDTO feeDTO = new FeeDTO();
        feeDTO.setId(1L);
        feeDTO.setBookFee(new BigDecimal(3500));
        feeDTO.setTuitionFee(new BigDecimal(15000));

        when(feeMapper.feeToFeeDTO(any(Fee.class))).thenReturn(feeDTO);

        StudentDTO studentDTO = studentMapper.studentAndFeeToStudentDTO(student,student.getFee());

        assertNotNull(studentDTO);
        assertThat(studentDTO.getStudentId().getStudentId(),is(equalTo(student.getStudentId().getStudentId())));
        assertThat(studentDTO.getFirstName(),is(equalTo(student.getFirstName())));
        assertThat(studentDTO.getFee().getBookFee(),is(equalTo(feeDTO.getBookFee())));
        assertThat(studentDTO.getFee().getTuitionFee(),is(equalTo(feeDTO.getTuitionFee())));

    }

    @Test
    public void studentDTOToStudent() {
    }

    @Test
    public void studentDTOToFee() {
    }
}