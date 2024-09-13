package com.example.paypal.repository;

import com.example.paypal.model.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository  extends JpaRepository<PaymentRecord,Long> {

    PaymentRecord findByPaymentId(String paymentId);
}
