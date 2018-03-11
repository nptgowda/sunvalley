package com.prashanth.sunvalley.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class MiscFee {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String feeName;
    private BigDecimal feeAmount;

    @ManyToOne
    @JoinColumn(name = "fee_id")
    private Fee fee;

}


