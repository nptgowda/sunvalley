package com.prashanth.sunvalley.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class StudentIdKeeper {

    @Id
    @GenericGenerator(name="student_id",
            strategy = "com.prashanth.sunvalley.util.StudentIdGenerator")
    @GeneratedValue(generator = "student_id")
    private String studentId;
}
