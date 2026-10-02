package com.hospital.payment.client;

import com.hospital.payment.dto.BillingResponseDto;
import com.hospital.payment.dto.BillingStatusUpdateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "billing-service")
public interface BillingClient {

    @GetMapping("/api/billings/{id}")
    BillingResponseDto getBillingById(
            @PathVariable("id") Long id);

    @PutMapping("/api/billings/{id}/status")
    BillingResponseDto updateBillingStatus(
            @PathVariable("id") Long id,
            @RequestBody BillingStatusUpdateDto request);
}