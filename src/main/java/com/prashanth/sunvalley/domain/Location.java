package com.prashanth.sunvalley.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Location {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;
    private BigDecimal transportFee;

    @OneToMany(mappedBy = "location")
    @JsonBackReference
    private List<Student> students = new ArrayList<>();

}
