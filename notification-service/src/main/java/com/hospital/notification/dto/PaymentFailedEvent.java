package com.hospital.notification.dto;

import com.hospital.notification.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentFailedEvent {

    private Long paymentId;
    private Long billId;
    private Long patientId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private LocalDateTime paymentDate;
}