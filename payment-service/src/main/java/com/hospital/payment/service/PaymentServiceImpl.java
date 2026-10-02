package com.hospital.payment.service;

import com.hospital.payment.client.AppointmentClient;
import com.hospital.payment.client.BillingClient;
import com.hospital.payment.dto.AppointmentResponseDto;
import com.hospital.payment.dto.BillingResponseDto;
import com.hospital.payment.dto.BillingStatusUpdateDto;
import com.hospital.payment.dto.PaymentRequestDto;
import com.hospital.payment.dto.PaymentResponseDto;
import com.hospital.payment.entity.Payment;
import com.hospital.payment.enums.AppointmentStatus;
import com.hospital.payment.enums.BillingStatus;
import com.hospital.payment.enums.PaymentStatus;
import com.hospital.payment.exception.BillingServiceException;
import com.hospital.payment.exception.PaymentNotFoundException;
import com.hospital.payment.repository.PaymentRepository;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.hospital.payment.exception.PaymentValidationException;

import com.hospital.payment.dto.PaymentCompletedEvent;
import com.hospital.payment.dto.PaymentFailedEvent;
import com.hospital.payment.kafka.PaymentFailedKafkaProducer;
import com.hospital.payment.kafka.PaymentKafkaProducer;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;
	private final BillingClient billingClient;
	private final AppointmentClient appointmentClient;
	private final PaymentKafkaProducer paymentKafkaProducer;
	private final PaymentFailedKafkaProducer paymentFailedKafkaProducer;

	@Override
	public PaymentResponseDto createPayment(PaymentRequestDto request) {

		// Get bill details from Billing Service
		BillingResponseDto billing;

		try {

			billing = billingClient.getBillingById(request.getBillId());

		} catch (FeignException.NotFound ex) {

			throw new BillingServiceException("Billing not found with id: " + request.getBillId());
		}

		// Check patient belongs to the bill
		if (!billing.getPatientId().equals(request.getPatientId())) {

			throw new PaymentValidationException("Patient ID does not match the selected bill");
		}

		// Get appointment details from Appointment Service
		AppointmentResponseDto appointment;

		try {

			appointment = appointmentClient.getAppointmentById(billing.getAppointmentId());

		} catch (FeignException.NotFound ex) {

			throw new PaymentValidationException("Appointment not found with id: " + billing.getAppointmentId());
		}

		// Check patient belongs to the appointment
		if (!appointment.getPatientId().equals(request.getPatientId())) {

			throw new PaymentValidationException("Patient ID does not match the appointment");
		}

		// Check ₹200 advance payment rule
		if (appointment.getStatus() != AppointmentStatus.COMPLETED) {

			List<Payment> existingSuccessfulPayments = paymentRepository.findByBillId(request.getBillId()).stream()
					.filter(payment -> payment.getPaymentStatus() == PaymentStatus.SUCCESS).toList();

			BigDecimal totalPaid = existingSuccessfulPayments.stream().map(Payment::getAmount).reduce(BigDecimal.ZERO,
					BigDecimal::add);

			// First successful payment before treatment
			// must be exactly ₹200
			if (totalPaid.compareTo(BigDecimal.ZERO) == 0) {

				if (request.getAmount().compareTo(new BigDecimal("200")) != 0) {

					throw new PaymentValidationException(
							"Advance payment must be exactly ₹200 before treatment is completed");
				}
			}
		}

		// Get all payments already made for this bill
		List<Payment> existingPayments = paymentRepository.findByBillId(request.getBillId());

		// Calculate total successful payments
		BigDecimal totalPaid = existingPayments.stream()
				.filter(payment -> payment.getPaymentStatus() == PaymentStatus.SUCCESS).map(Payment::getAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		// Calculate remaining amount
		BigDecimal remainingAmount = billing.getTotalAmount().subtract(totalPaid);

		// Check whether bill is already fully paid
		if (remainingAmount.compareTo(BigDecimal.ZERO) <= 0) {

			throw new PaymentValidationException("This bill is already fully paid");
		}

		// Check whether new payment exceeds remaining amount
		if (request.getAmount().compareTo(remainingAmount) > 0) {

			throw new PaymentValidationException(
					"Payment amount exceeds remaining bill amount. " + "Remaining amount: " + remainingAmount);
		}

		// Create payment
		Payment payment = new Payment();

		payment.setBillId(request.getBillId());
		payment.setPatientId(request.getPatientId());
		payment.setAmount(request.getAmount());
		payment.setPaymentMethod(request.getPaymentMethod());
		payment.setPaymentStatus(request.getPaymentStatus());
		payment.setPaymentDate(LocalDateTime.now());

		Payment savedPayment = paymentRepository.save(payment);

		updateBillingStatus(billing, request.getBillId());

		if (savedPayment.getPaymentStatus() == PaymentStatus.SUCCESS) {

			PaymentCompletedEvent event = new PaymentCompletedEvent(savedPayment.getId(), savedPayment.getBillId(),
					savedPayment.getPatientId(), savedPayment.getAmount(), savedPayment.getPaymentMethod(),
					savedPayment.getPaymentDate());

			paymentKafkaProducer.sendPaymentCompletedEvent(event);
		}
		if (savedPayment.getPaymentStatus() == PaymentStatus.FAILED) {

		    PaymentFailedEvent event = new PaymentFailedEvent(
		            savedPayment.getId(),
		            savedPayment.getBillId(),
		            savedPayment.getPatientId(),
		            savedPayment.getAmount(),
		            savedPayment.getPaymentMethod(),
		            savedPayment.getPaymentDate()
		    );

		    paymentFailedKafkaProducer.sendPaymentFailedEvent(event);
		}

		return mapToResponse(savedPayment);
	}

	@Override
	public PaymentResponseDto getPaymentById(Long id) {

		Payment payment = paymentRepository.findById(id)
				.orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));

		return mapToResponse(payment);
	}

	@Override
	public List<PaymentResponseDto> getAllPayments() {

		return paymentRepository.findAll().stream().map(this::mapToResponse).toList();
	}

	@Override
	public List<PaymentResponseDto> getPaymentsByBillId(Long billId) {

		return paymentRepository.findByBillId(billId).stream().map(this::mapToResponse).toList();
	}

	@Override
	public List<PaymentResponseDto> getPaymentsByPatientId(Long patientId) {

		return paymentRepository.findByPatientId(patientId).stream().map(this::mapToResponse).toList();
	}

	@Override
	public PaymentResponseDto updatePayment(Long id, PaymentRequestDto request) {

		Payment payment = paymentRepository.findById(id)
				.orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));

		BillingResponseDto billing;

		try {

			billing = billingClient.getBillingById(request.getBillId());

		} catch (FeignException.NotFound ex) {

			throw new BillingServiceException("Billing not found with id: " + request.getBillId());
		}

		// Check patient belongs to the bill

		if (!billing.getPatientId().equals(request.getPatientId())) {

			throw new PaymentValidationException("Patient ID does not match the selected bill");
		}

		/*
		 * Calculate successful payments excluding the payment currently being updated.
		 */
		List<Payment> existingPayments = paymentRepository.findByBillId(request.getBillId());

		BigDecimal totalPaid = existingPayments.stream().filter(existingPayment -> !existingPayment.getId().equals(id))
				.filter(existingPayment -> existingPayment.getPaymentStatus() == PaymentStatus.SUCCESS)
				.map(Payment::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

		/*
		 * Only SUCCESS payments contribute to the bill's paid amount.
		 */
		if (request.getPaymentStatus() == PaymentStatus.SUCCESS) {

			BigDecimal remainingAmount = billing.getTotalAmount().subtract(totalPaid);

			if (remainingAmount.compareTo(BigDecimal.ZERO) <= 0) {
				throw new PaymentValidationException("This bill is already fully paid");
			}

			if (request.getAmount().compareTo(remainingAmount) > 0) {
				throw new PaymentValidationException(
						"Payment amount exceeds remaining bill amount. " + "Remaining amount: " + remainingAmount);
			}
		}

		payment.setBillId(request.getBillId());
		payment.setPatientId(request.getPatientId());
		payment.setAmount(request.getAmount());
		payment.setPaymentMethod(request.getPaymentMethod());
		payment.setPaymentStatus(request.getPaymentStatus());

		Payment updatedPayment = paymentRepository.save(payment);

		/*
		 * Recalculate billing status after update.
		 */
		updateBillingStatus(billing, request.getBillId());
		return mapToResponse(updatedPayment);
	}

	@Override
	public void deletePayment(Long id) {

		Payment payment = paymentRepository.findById(id)
				.orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));

		Long billId = payment.getBillId();

		BillingResponseDto billing;

		try {

			billing = billingClient.getBillingById(billId);

		} catch (FeignException.NotFound ex) {

			throw new BillingServiceException("Billing not found with id: " + billId);
		}

		paymentRepository.delete(payment);
		paymentRepository.flush();

		/*
		 * Recalculate billing status after deleting the payment.
		 */
		updateBillingStatus(billing, billId);
	}

	private void updateBillingStatus(BillingResponseDto billing, Long billId) {
		List<Payment> payments = paymentRepository.findByBillId(billId);

		BigDecimal totalPaid = payments.stream().filter(payment -> payment.getPaymentStatus() == PaymentStatus.SUCCESS)
				.map(Payment::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

		BillingStatus billingStatus;

		if (totalPaid.compareTo(billing.getTotalAmount()) >= 0) {

			billingStatus = BillingStatus.PAID;

		} else if (totalPaid.compareTo(BigDecimal.ZERO) > 0) {

			billingStatus = BillingStatus.PARTIALLY_PAID;

		} else {

			billingStatus = BillingStatus.GENERATED;
		}

		BillingStatusUpdateDto request = new BillingStatusUpdateDto();

		request.setBillingStatus(billingStatus);

		billingClient.updateBillingStatus(billId, request);
	}

	private PaymentResponseDto mapToResponse(Payment payment) {

		return new PaymentResponseDto(payment.getId(), payment.getBillId(), payment.getPatientId(), payment.getAmount(),
				payment.getPaymentMethod(), payment.getPaymentStatus(), payment.getPaymentDate());
	}
}