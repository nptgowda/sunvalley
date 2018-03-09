package com.prashanth.sunvalley.service;

import com.prashanth.sunvalley.Model.PaymentDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PaymentService {

    List<PaymentDTO> getAllPaymentsOfStudent(String studentId);

    PaymentDTO createPayment(String studentId, PaymentDTO paymentDTO);

    @Transactional
    void deletePaymentById(String studentId, long paymentId);
}
