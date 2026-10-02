package com.hospital.medicine.controller;

import com.hospital.medicine.dto.BillingResponseDto;
import com.hospital.medicine.dto.MedicineChargeRequestDto;
import com.hospital.medicine.dto.MedicinePrescriptionRequestDto;
import com.hospital.medicine.dto.MedicineRequestDto;
import com.hospital.medicine.dto.MedicineResponseDto;
import com.hospital.medicine.service.MedicineService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
@RequiredArgsConstructor
public class MedicineController {

	private final MedicineService medicineService;

	@PostMapping
	public ResponseEntity<MedicineResponseDto> createMedicine(@Valid @RequestBody MedicineRequestDto request) {

		MedicineResponseDto response = medicineService.createMedicine(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MedicineResponseDto> getMedicineById(@PathVariable Long id) {

		MedicineResponseDto response = medicineService.getMedicineById(id);

		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<List<MedicineResponseDto>> getAllMedicines() {

		List<MedicineResponseDto> response = medicineService.getAllMedicines();

		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<MedicineResponseDto> updateMedicine(@PathVariable Long id,
			@Valid @RequestBody MedicineRequestDto request) {

		MedicineResponseDto response = medicineService.updateMedicine(id, request);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {

		medicineService.deleteMedicine(id);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/billing/{billId}")
	public ResponseEntity<BillingResponseDto> getBillingById(@PathVariable Long billId) {

		BillingResponseDto response = medicineService.getBillingById(billId);

		return ResponseEntity.ok(response);
	}

	@PutMapping("/billing/{billId}/medicine-fee")
	public ResponseEntity<BillingResponseDto> updateMedicineFee(@PathVariable Long billId,
			@Valid @RequestBody MedicineChargeRequestDto request) {

		BillingResponseDto response = medicineService.updateMedicineFee(billId, request);

		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/prescribe")
	public ResponseEntity<MedicineResponseDto> prescribeMedicine(
	        @Valid @RequestBody MedicinePrescriptionRequestDto request) {

	    MedicineResponseDto response =
	            medicineService.prescribeMedicine(request);

	    return ResponseEntity.ok(response);
	}
}
