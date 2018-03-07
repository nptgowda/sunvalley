package com.prashanth.sunvalley.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class StudentIdKeeper {

    @Id
    @GenericGenerator(name="student_id",
            strategy = "com.prashanth.sunvalley.util.StudentIdGenerator")
    @GeneratedValue(generator = "student_id")
    private String studentId;
}
