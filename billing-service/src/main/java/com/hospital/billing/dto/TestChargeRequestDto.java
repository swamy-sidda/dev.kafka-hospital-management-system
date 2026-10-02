package com.hospital.billing.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TestChargeRequestDto {

    @NotNull(message = "Test fee is required")
    @PositiveOrZero(message = "Test fee cannot be negative")
    private BigDecimal testFee;
}