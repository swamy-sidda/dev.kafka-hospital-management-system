package com.hospital.billing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicinePrescribedEvent {

    private Long medicineId;
    private Long billId;
    private Integer quantity;
    private BigDecimal medicineCharge;
}