package com.prashanth.sunvalley.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Student student;

    @OneToMany(mappedBy = "fee")
    private List<Payment> payments;

    private BigDecimal tuitionFee;
    private BigDecimal bookFee;
    private BigDecimal uniformFee;
    private BigDecimal transportFee;
    private BigDecimal oldBalance;

    @OneToMany(mappedBy = "fee")
    private List<MiscFee> miscFee;
}
