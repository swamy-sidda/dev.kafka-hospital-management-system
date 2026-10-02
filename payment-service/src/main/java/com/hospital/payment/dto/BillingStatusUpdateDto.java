package com.hospital.payment.dto;

import com.hospital.payment.enums.BillingStatus;
import lombok.Data;

@Data
public class BillingStatusUpdateDto {

    private BillingStatus billingStatus;
}