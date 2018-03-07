package com.prashanth.sunvalley.Model;

import com.prashanth.sunvalley.domain.StudentIdKeeper;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StudentDTO {

    private StudentIdKeeper studentId;
    private String firstName;
    private String lastName;
    private String Initial;
    private String fathersName;
    private String fathersNumber;
    private String mothersName;
    private String mothersNumber;
    private LocalDate dateOfBirth;
    private String email;
    private LocalDateTime dateCreated;

    private FeeDTO fee;
    private LocationDTO location;
    private GradeDTO grade;
}
