package com.prashanth.sunvalley.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Grade {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String grade;
    private String section;
    private BigDecimal tuitionFee;
    private BigDecimal uniformFee;
    private BigDecimal bookFee;

    @OneToMany(mappedBy = "grade")
    private List<Student> students = new ArrayList<>();
}
