package com.hospital.payment.dto;

import com.hospital.payment.enums.PaymentMethod;
import com.hospital.payment.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequestDto {

    @NotNull(message = "Bill ID is required")
    private Long billId;

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Payment amount is required")
    @Positive(message = "Payment amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    @NotNull(message = "Payment status is required")
    private PaymentStatus paymentStatus;
}