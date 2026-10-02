package com.hospital.billing.entity;

import com.hospital.billing.enums.BillingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "billing")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    
    private Long appointmentId;

    private BigDecimal consultationFee;

    private BigDecimal medicineFee;

    private BigDecimal testFee;

    private BigDecimal otherCharges;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private BillingStatus billingStatus;

    private LocalDateTime billingDate;
}