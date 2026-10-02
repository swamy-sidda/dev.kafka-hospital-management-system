package com.hospital.billing.dto;

import com.hospital.billing.enums.BillingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BillingStatusUpdateDto {

    @NotNull(message = "Billing status is required")
    private BillingStatus billingStatus;
}