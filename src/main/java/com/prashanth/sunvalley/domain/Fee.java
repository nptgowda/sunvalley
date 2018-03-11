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
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonBackReference
    private Student student;

    @OneToMany(mappedBy = "fee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    private BigDecimal tuitionFee;
    private BigDecimal bookFee;
    private BigDecimal uniformFee;
    private BigDecimal transportFee;
    private BigDecimal oldBalance;
    private BigDecimal concession;
    @OneToMany(mappedBy = "fee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MiscFee> miscFee = new ArrayList<>();
    private BigDecimal totalMiscFeeAmount = new BigDecimal(0);

}
