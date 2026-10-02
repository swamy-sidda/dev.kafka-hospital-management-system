package com.hospital.medicine.dto;

import com.hospital.medicine.enums.BillingStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingResponseDto {

    private Long id;

    private Long appointmentId;

    private Long patientId;

    private BigDecimal consultationFee;

    private BigDecimal medicineFee;

    private BigDecimal testFee;

    private BigDecimal otherCharges;

    private BigDecimal totalAmount;

    private BillingStatus billingStatus;

    private LocalDateTime billingDate;
}