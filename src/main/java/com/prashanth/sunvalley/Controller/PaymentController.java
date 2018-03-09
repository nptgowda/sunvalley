package com.prashanth.sunvalley.Controller;

import com.prashanth.sunvalley.Model.PaymentDTO;
import com.prashanth.sunvalley.Model.PaymentListDTO;
import com.prashanth.sunvalley.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/students/{studentId}/payments")
    @ResponseStatus(HttpStatus.OK)
    public PaymentListDTO getAllPaymentsOfStudent(@PathVariable  String studentId){
        return new PaymentListDTO(paymentService.getAllPaymentsOfStudent(studentId));
    }

    @PostMapping("/students/{studentId}/payments")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentDTO createPayment(@PathVariable String studentId, @RequestBody PaymentDTO paymentDTO){
        return paymentService.createPayment(studentId,paymentDTO);
    }

    @DeleteMapping("/students/{studentId}/payments/{paymentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePaymentById(@PathVariable String studentId, @PathVariable Long paymentId){
        paymentService.deletePaymentById(studentId,paymentId);
    }
}
