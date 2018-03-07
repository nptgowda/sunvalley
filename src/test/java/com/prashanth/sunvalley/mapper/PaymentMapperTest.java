package com.prashanth.sunvalley.mapper;

import com.prashanth.sunvalley.Model.PaymentDTO;
import com.prashanth.sunvalley.domain.Payment;
import com.prashanth.sunvalley.domain.PaymentType;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class PaymentMapperTest {

    PaymentMapper paymentMapper;
    @Before
    public void setUp() throws Exception {
        paymentMapper = PaymentMapper.INSTANCE;
    }

    @Test
    public void paymentToPaymentDTO() throws Exception {
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setAmount(new BigDecimal(5000));
        payment.setDate(LocalDate.now());
        payment.setPaymentType(PaymentType.TUITION);
        payment.setMiscPaymentName("Prashanth");
        PaymentDTO paymentDTO = paymentMapper.paymentToPaymentDTO(payment);

        assertNotNull(paymentDTO);
        assertThat(paymentDTO.getId(),is(equalTo(payment.getId())));
        assertThat(paymentDTO.getAmount(),is(equalTo(payment.getAmount())));
        assertThat(paymentDTO.getDate(),is(equalTo(payment.getDate())));
        assertThat(paymentDTO.getPaymentType(),is(equalTo(payment.getPaymentType())));
        assertThat(paymentDTO.getMiscPaymentName(),is(equalTo(payment.getMiscPaymentName())));
    }
}