package com.hospital.billing.dto;

import com.hospital.billing.enums.BillingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BillingRequestDto {

    @NotNull(message = "Appointment ID is required")
    private Long appointmentId;

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Consultation fee is required")
    private BigDecimal consultationFee;

    private BigDecimal medicineFee;
    private BigDecimal testFee;
    private BigDecimal otherCharges;

    @NotNull(message = "Billing status is required")
    private BillingStatus billingStatus;
}