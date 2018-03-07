package com.prashanth.sunvalley.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
public class Student {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
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

    @Lob
    private Byte[] image;

    private LocalDateTime dateCreated;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private Fee fee;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;



}
