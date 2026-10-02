package com.hospital.payment.controller;

import com.hospital.payment.dto.PaymentRequestDto;
import com.hospital.payment.dto.PaymentResponseDto;
import com.hospital.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDto> createPayment(
            @Valid @RequestBody PaymentRequestDto request) {

        PaymentResponseDto response = paymentService.createPayment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponseDto>> getAllPayments() {

        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> getPaymentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @GetMapping("/bill/{billId}")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsByBillId(
            @PathVariable Long billId) {

        return ResponseEntity.ok(
                paymentService.getPaymentsByBillId(billId)
        );
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsByPatientId(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                paymentService.getPaymentsByPatientId(patientId)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> updatePayment(
            @PathVariable Long id,
            @Valid @RequestBody PaymentRequestDto request) {

        return ResponseEntity.ok(
                paymentService.updatePayment(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(
            @PathVariable Long id) {

        paymentService.deletePayment(id);

        return ResponseEntity.ok("Payment deleted successfully");
    }
}