package com.prashanth.sunvalley.Model;

import com.prashanth.sunvalley.domain.StudentIdKeeper;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentUpdateDTO {
    private String firstName;
    private String lastName;
    private String Initial;
    private String fathersName;
    private String fathersNumber;
    private String mothersName;
    private String mothersNumber;
    private LocalDate dateOfBirth;
    private String email;
}
