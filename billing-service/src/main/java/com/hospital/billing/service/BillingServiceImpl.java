package com.hospital.billing.service;

import com.hospital.billing.dto.BillingRequestDto;
import com.hospital.billing.dto.BillingResponseDto;
import com.hospital.billing.dto.BillingStatusUpdateDto;
import com.hospital.billing.dto.MedicineChargeRequestDto;
import com.hospital.billing.dto.TestChargeRequestDto;
import com.hospital.billing.entity.Billing;
import com.hospital.billing.exception.BillingNotFoundException;
import com.hospital.billing.exception.BillingValidationException;
import com.hospital.billing.repository.BillingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

	private final BillingRepository billingRepository;

	@Override
	public BillingResponseDto createBilling(BillingRequestDto request) {

		Optional<Billing> existingBilling = billingRepository.findByAppointmentId(request.getAppointmentId());

		if (existingBilling.isPresent()) {
			throw new BillingValidationException(
					"Billing already exists for appointment id: " + request.getAppointmentId());
		}

		Billing billing = new Billing();

		billing.setAppointmentId(request.getAppointmentId());
		billing.setPatientId(request.getPatientId());
		billing.setConsultationFee(request.getConsultationFee());
		billing.setMedicineFee(request.getMedicineFee());
		billing.setTestFee(request.getTestFee());
		billing.setOtherCharges(request.getOtherCharges());
		billing.setBillingStatus(request.getBillingStatus());

		billing.setTotalAmount(calculateTotal(request));
		billing.setBillingDate(LocalDateTime.now());

		Billing savedBilling = billingRepository.save(billing);

		return mapToResponse(savedBilling);
	}

	@Override
	public BillingResponseDto getBillingById(Long id) {

		Billing billing = billingRepository.findById(id)
				.orElseThrow(() -> new BillingNotFoundException("Billing not found with id: " + id));

		return mapToResponse(billing);
	}

	@Override
	public List<BillingResponseDto> getAllBillings() {

		return billingRepository.findAll().stream().map(this::mapToResponse).toList();
	}

	@Override
	public BillingResponseDto updateBilling(Long id, BillingRequestDto request) {

		Billing billing = billingRepository.findById(id)
				.orElseThrow(() -> new BillingNotFoundException("Billing not found with id: " + id));

		billing.setAppointmentId(request.getAppointmentId());
		billing.setPatientId(request.getPatientId());
		billing.setConsultationFee(request.getConsultationFee());
		billing.setMedicineFee(request.getMedicineFee());
		billing.setTestFee(request.getTestFee());
		billing.setOtherCharges(request.getOtherCharges());
		billing.setBillingStatus(request.getBillingStatus());

		billing.setTotalAmount(calculateTotal(request));

		Billing updatedBilling = billingRepository.save(billing);

		return mapToResponse(updatedBilling);
	}

	@Override
	public void deleteBilling(Long id) {

		Billing billing = billingRepository.findById(id)
				.orElseThrow(() -> new BillingNotFoundException("Billing not found with id: " + id));

		billingRepository.delete(billing);
	}

	private BigDecimal calculateTotal(BillingRequestDto request) {

		BigDecimal consultation = request.getConsultationFee() != null ? request.getConsultationFee() : BigDecimal.ZERO;

		BigDecimal medicine = request.getMedicineFee() != null ? request.getMedicineFee() : BigDecimal.ZERO;

		BigDecimal test = request.getTestFee() != null ? request.getTestFee() : BigDecimal.ZERO;

		BigDecimal other = request.getOtherCharges() != null ? request.getOtherCharges() : BigDecimal.ZERO;

		return consultation.add(medicine).add(test).add(other);
	}

	private BillingResponseDto mapToResponse(Billing billing) {

		return new BillingResponseDto(billing.getId(), billing.getAppointmentId(), billing.getPatientId(),
				billing.getConsultationFee(), billing.getMedicineFee(), billing.getTestFee(), billing.getOtherCharges(),
				billing.getTotalAmount(), billing.getBillingStatus(), billing.getBillingDate());
	}

	@Override
	public BillingResponseDto updateBillingStatus(Long id, BillingStatusUpdateDto request) {

		Billing billing = billingRepository.findById(id)
				.orElseThrow(() -> new BillingNotFoundException("Billing not found with id: " + id));

		billing.setBillingStatus(request.getBillingStatus());

		Billing updatedBilling = billingRepository.save(billing);

		return mapToResponse(updatedBilling);
	}

	@Override
	public BillingResponseDto updateMedicineFee(Long billId, MedicineChargeRequestDto request) {

		Billing billing = billingRepository.findById(billId)
				.orElseThrow(() -> new BillingNotFoundException("Billing not found with id: " + billId));

		BigDecimal currentMedicineFee = billing.getMedicineFee();

		if (currentMedicineFee == null) {
			currentMedicineFee = BigDecimal.ZERO;
		}

		BigDecimal updatedMedicineFee = currentMedicineFee.add(request.getMedicineFee());

		billing.setMedicineFee(updatedMedicineFee);

		BigDecimal consultationFee = billing.getConsultationFee() == null ? BigDecimal.ZERO
				: billing.getConsultationFee();

		BigDecimal medicineFee = billing.getMedicineFee() == null ? BigDecimal.ZERO : billing.getMedicineFee();

		BigDecimal testFee = billing.getTestFee() == null ? BigDecimal.ZERO : billing.getTestFee();

		BigDecimal otherCharges = billing.getOtherCharges() == null ? BigDecimal.ZERO : billing.getOtherCharges();

		BigDecimal totalAmount = consultationFee.add(medicineFee).add(testFee).add(otherCharges);

		billing.setTotalAmount(totalAmount);

		Billing updatedBilling = billingRepository.save(billing);

		return mapToResponse(updatedBilling);
	}

	@Override
	public BillingResponseDto updateTestFee(Long billId, TestChargeRequestDto request) {

		Billing billing = billingRepository.findById(billId)
				.orElseThrow(() -> new BillingNotFoundException("Billing not found with id: " + billId));

		BigDecimal currentTestFee = billing.getTestFee();

		if (currentTestFee == null) {
			currentTestFee = BigDecimal.ZERO;
		}

		BigDecimal updatedTestFee = currentTestFee.add(request.getTestFee());

		billing.setTestFee(updatedTestFee);

		BigDecimal consultationFee = billing.getConsultationFee() == null ? BigDecimal.ZERO
				: billing.getConsultationFee();

		BigDecimal medicineFee = billing.getMedicineFee() == null ? BigDecimal.ZERO : billing.getMedicineFee();

		BigDecimal testFee = billing.getTestFee() == null ? BigDecimal.ZERO : billing.getTestFee();

		BigDecimal otherCharges = billing.getOtherCharges() == null ? BigDecimal.ZERO : billing.getOtherCharges();

		BigDecimal totalAmount = consultationFee.add(medicineFee).add(testFee).add(otherCharges);

		billing.setTotalAmount(totalAmount);

		Billing updatedBilling = billingRepository.save(billing);

		return mapToResponse(updatedBilling);
	}
}