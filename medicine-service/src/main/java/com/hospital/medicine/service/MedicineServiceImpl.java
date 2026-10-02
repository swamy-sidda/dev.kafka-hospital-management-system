package com.hospital.medicine.service;

import com.hospital.medicine.client.BillingClient;
import com.hospital.medicine.dto.BillingResponseDto;
import com.hospital.medicine.dto.MedicineChargeRequestDto;
import com.hospital.medicine.dto.MedicinePrescribedEvent;
import com.hospital.medicine.dto.MedicinePrescriptionRequestDto;
import com.hospital.medicine.dto.MedicineRequestDto;
import com.hospital.medicine.dto.MedicineResponseDto;
import com.hospital.medicine.entity.Medicine;
import com.hospital.medicine.exception.InsufficientStockException;
import com.hospital.medicine.exception.MedicineNotFoundException;
import com.hospital.medicine.kafka.MedicineKafkaProducer;
import com.hospital.medicine.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {

	private final MedicineRepository medicineRepository;

	private final BillingClient billingClient;

	private final MedicineKafkaProducer medicineKafkaProducer;

	@Override
	public BillingResponseDto getBillingById(Long billId) {
		return billingClient.getBillingById(billId);
	}

	@Override
	public MedicineResponseDto createMedicine(MedicineRequestDto request) {

		Medicine medicine = new Medicine();

		medicine.setName(request.getName());
		medicine.setDescription(request.getDescription());
		medicine.setPrice(request.getPrice());
		medicine.setStockQuantity(request.getStockQuantity());
		medicine.setAvailable(request.getAvailable());

		Medicine savedMedicine = medicineRepository.save(medicine);

		return mapToResponse(savedMedicine);
	}

	@Override
	public MedicineResponseDto getMedicineById(Long id) {

		Medicine medicine = medicineRepository.findById(id)
				.orElseThrow(() -> new MedicineNotFoundException("Medicine not found with id: " + id));

		return mapToResponse(medicine);
	}

	@Override
	public List<MedicineResponseDto> getAllMedicines() {

		return medicineRepository.findAll().stream().map(this::mapToResponse).toList();
	}

	@Override
	public MedicineResponseDto updateMedicine(Long id, MedicineRequestDto request) {

		Medicine medicine = medicineRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Medicine not found with id: " + id));

		medicine.setName(request.getName());
		medicine.setDescription(request.getDescription());
		medicine.setPrice(request.getPrice());
		medicine.setStockQuantity(request.getStockQuantity());
		medicine.setAvailable(request.getAvailable());

		Medicine updatedMedicine = medicineRepository.save(medicine);

		return mapToResponse(updatedMedicine);
	}

	@Override
	public void deleteMedicine(Long id) {

		Medicine medicine = medicineRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Medicine not found with id: " + id));

		medicineRepository.delete(medicine);
	}

	private MedicineResponseDto mapToResponse(Medicine medicine) {

		return new MedicineResponseDto(medicine.getId(), medicine.getName(), medicine.getDescription(),
				medicine.getPrice(), medicine.getStockQuantity(), medicine.getAvailable());
	}

	@Override
	public BillingResponseDto updateMedicineFee(Long billId, MedicineChargeRequestDto request) {

		return billingClient.updateMedicineFee(billId, request);
	}

	@Override
	public MedicineResponseDto prescribeMedicine(MedicinePrescriptionRequestDto request) {

		Medicine medicine = medicineRepository.findById(request.getMedicineId()).orElseThrow(
				() -> new MedicineNotFoundException("Medicine not found with id: " + request.getMedicineId()));

		if (!Boolean.TRUE.equals(medicine.getAvailable())) {
			throw new InsufficientStockException("Medicine is currently unavailable: " + medicine.getName());
		}

		if (medicine.getStockQuantity() < request.getQuantity()) {
			throw new InsufficientStockException("Insufficient stock for medicine: " + medicine.getName()
					+ ". Available stock: " + medicine.getStockQuantity() + ", requested: " + request.getQuantity());
		}

		BigDecimal medicineCharge = medicine.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()));

		MedicinePrescribedEvent event = new MedicinePrescribedEvent(medicine.getId(), request.getBillId(),
				request.getQuantity(), medicineCharge);

		medicineKafkaProducer.sendMedicinePrescribedEvent(event);

		medicine.setStockQuantity(medicine.getStockQuantity() - request.getQuantity());

		Medicine updatedMedicine = medicineRepository.save(medicine);

		return mapToResponse(updatedMedicine);
	}
}