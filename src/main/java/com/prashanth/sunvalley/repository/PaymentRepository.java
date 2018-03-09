package com.prashanth.sunvalley.repository;

import com.prashanth.sunvalley.domain.Payment;
import com.prashanth.sunvalley.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByFeeStudent(Student student);
}
