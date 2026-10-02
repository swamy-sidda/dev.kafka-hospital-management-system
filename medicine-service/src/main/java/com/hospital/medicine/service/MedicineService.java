package com.hospital.medicine.service;

import com.hospital.medicine.dto.BillingResponseDto;
import com.hospital.medicine.dto.MedicineChargeRequestDto;
import com.hospital.medicine.dto.MedicinePrescriptionRequestDto;
import com.hospital.medicine.dto.MedicineRequestDto;
import com.hospital.medicine.dto.MedicineResponseDto;

import java.util.List;

public interface MedicineService {

	MedicineResponseDto createMedicine(MedicineRequestDto request);

	MedicineResponseDto getMedicineById(Long id);

	List<MedicineResponseDto> getAllMedicines();

	MedicineResponseDto updateMedicine(Long id, MedicineRequestDto request);

	void deleteMedicine(Long id);

	BillingResponseDto getBillingById(Long billId);

	BillingResponseDto updateMedicineFee(Long billId, MedicineChargeRequestDto request);

	MedicineResponseDto prescribeMedicine(MedicinePrescriptionRequestDto request);
}