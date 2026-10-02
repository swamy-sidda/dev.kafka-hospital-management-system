package com.hospital.payment.service;

import com.hospital.payment.dto.PaymentRequestDto;
import com.hospital.payment.dto.PaymentResponseDto;

import java.util.List;

public interface PaymentService {

    PaymentResponseDto createPayment(PaymentRequestDto request);

    PaymentResponseDto getPaymentById(Long id);

    List<PaymentResponseDto> getAllPayments();

    List<PaymentResponseDto> getPaymentsByBillId(Long billId);

    List<PaymentResponseDto> getPaymentsByPatientId(Long patientId);

    PaymentResponseDto updatePayment(Long id, PaymentRequestDto request);

    void deletePayment(Long id);
}