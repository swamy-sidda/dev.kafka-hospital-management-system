package com.hospital.billing.service;

import com.hospital.billing.dto.BillingRequestDto;
import com.hospital.billing.dto.BillingResponseDto;
import com.hospital.billing.dto.BillingStatusUpdateDto;
import com.hospital.billing.dto.MedicineChargeRequestDto;
import com.hospital.billing.dto.TestChargeRequestDto;

import java.util.List;

public interface BillingService {

	BillingResponseDto createBilling(BillingRequestDto request);

	BillingResponseDto getBillingById(Long id);

	List<BillingResponseDto> getAllBillings();

	BillingResponseDto updateBilling(Long id, BillingRequestDto request);

	void deleteBilling(Long id);

	BillingResponseDto updateBillingStatus(Long id, BillingStatusUpdateDto request);

	BillingResponseDto updateMedicineFee(Long billId, MedicineChargeRequestDto request);

	BillingResponseDto updateTestFee(Long billId, TestChargeRequestDto request);
}