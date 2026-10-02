package com.hospital.medicine.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineRequestDto {

    @NotBlank(message = "Medicine name is required")
    private String name;

    private String description;

    @NotNull(message = "Medicine price is required")
    @PositiveOrZero(message = "Medicine price cannot be negative")
    private BigDecimal price;

    @NotNull(message = "Stock quantity is required")
    @PositiveOrZero(message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    @NotNull(message = "Availability is required")
    private Boolean available;
}