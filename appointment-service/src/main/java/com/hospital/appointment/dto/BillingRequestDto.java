package com.hospital.appointment.dto;

import com.hospital.appointment.enums.BillingStatus;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BillingRequestDto {

    private Long appointmentId;

    private Long patientId;

    private BigDecimal consultationFee;

    private BigDecimal medicineFee;

    private BigDecimal testFee;

    private BigDecimal otherCharges;

    private BillingStatus billingStatus;
}