package com.prashanth.sunvalley.Model;

import com.prashanth.sunvalley.domain.Fee;
import com.prashanth.sunvalley.domain.PaymentType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentDTO {

    private Long id;
    private PaymentType paymentType;
    private Fee fee;
    private BigDecimal amount;
    private LocalDate date;
    private String miscPaymentName;

}
