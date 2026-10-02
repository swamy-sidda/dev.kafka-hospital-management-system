package com.hospital.billing.controller;

import com.hospital.billing.dto.BillingRequestDto;
import com.hospital.billing.dto.BillingResponseDto;
import com.hospital.billing.dto.BillingStatusUpdateDto;
import com.hospital.billing.dto.MedicineChargeRequestDto;
import com.hospital.billing.dto.TestChargeRequestDto;
import com.hospital.billing.service.BillingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billings")
@RequiredArgsConstructor
public class BillingController {

	private final BillingService billingService;

	@PostMapping
	public ResponseEntity<BillingResponseDto> createBilling(@Valid @RequestBody BillingRequestDto request) {

		BillingResponseDto response = billingService.createBilling(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{id}/medicine-fee")
	public ResponseEntity<BillingResponseDto> updateMedicineFee(@PathVariable Long id,
			@Valid @RequestBody MedicineChargeRequestDto request) {

		BillingResponseDto response = billingService.updateMedicineFee(id, request);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BillingResponseDto> getBillingById(@PathVariable Long id) {

		return ResponseEntity.ok(billingService.getBillingById(id));
	}

	@GetMapping
	public ResponseEntity<List<BillingResponseDto>> getAllBillings() {

		return ResponseEntity.ok(billingService.getAllBillings());
	}

	@PutMapping("/{id}")
	public ResponseEntity<BillingResponseDto> updateBilling(@PathVariable Long id,
			@Valid @RequestBody BillingRequestDto request) {

		return ResponseEntity.ok(billingService.updateBilling(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteBilling(@PathVariable Long id) {

		billingService.deleteBilling(id);

		return ResponseEntity.ok("Billing deleted successfully");
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<BillingResponseDto> updateBillingStatus(@PathVariable Long id,
			@Valid @RequestBody BillingStatusUpdateDto request) {

		return ResponseEntity.ok(billingService.updateBillingStatus(id, request));
	}
	
	@PutMapping("/{id}/test-fee")
	public ResponseEntity<BillingResponseDto> updateTestFee(
	        @PathVariable Long id,
	        @Valid @RequestBody TestChargeRequestDto request) {

	    BillingResponseDto response =
	            billingService.updateTestFee(id, request);

	    return ResponseEntity.ok(response);
	}
}