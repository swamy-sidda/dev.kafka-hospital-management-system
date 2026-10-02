package com.hospital.medicine.client;

import com.hospital.medicine.dto.BillingResponseDto;
import com.hospital.medicine.dto.MedicineChargeRequestDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "billing-service")
public interface BillingClient {

    @GetMapping("/api/billings/{id}")
    BillingResponseDto getBillingById(
            @PathVariable("id") Long id
    );

    @PutMapping("/api/billings/{id}/medicine-fee")
    BillingResponseDto updateMedicineFee(
            @PathVariable("id") Long id,
            @RequestBody MedicineChargeRequestDto request
    );
}