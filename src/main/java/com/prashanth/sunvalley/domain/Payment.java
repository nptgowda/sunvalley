package com.prashanth.sunvalley.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Payment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "fee_id")
    private Fee fee;

    private BigDecimal amount;
    private LocalDate date;
    private String miscPaymentName;

}
