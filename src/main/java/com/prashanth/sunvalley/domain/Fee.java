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
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Student student;

    @OneToMany(mappedBy = "fee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    private BigDecimal tuitionFee;
    private BigDecimal bookFee;
    private BigDecimal uniformFee;
    private BigDecimal transportFee;
    private BigDecimal oldBalance;

    @OneToMany(mappedBy = "fee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MiscFee> miscFee;
}
