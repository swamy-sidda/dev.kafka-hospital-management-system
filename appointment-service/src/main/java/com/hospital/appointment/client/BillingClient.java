package com.hospital.appointment.client;

import com.hospital.appointment.dto.BillingRequestDto;
import com.hospital.appointment.dto.BillingResponseDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "billing-service")
public interface BillingClient {

    @PostMapping("/api/billings")
    BillingResponseDto createBilling(
            @RequestBody BillingRequestDto request
    );
}