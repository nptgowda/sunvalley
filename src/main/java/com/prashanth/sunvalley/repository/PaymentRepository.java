package com.prashanth.sunvalley.repository;

import com.prashanth.sunvalley.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
