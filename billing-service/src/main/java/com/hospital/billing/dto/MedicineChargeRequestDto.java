package com.hospital.billing.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MedicineChargeRequestDto {

    @NotNull(message = "Medicine fee is required")
    @PositiveOrZero(message = "Medicine fee cannot be negative")
    private BigDecimal medicineFee;
}