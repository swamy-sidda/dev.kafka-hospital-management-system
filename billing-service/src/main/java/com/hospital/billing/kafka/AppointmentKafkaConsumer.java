package com.hospital.billing.kafka;

import com.hospital.billing.dto.AppointmentCreatedEvent;
import com.hospital.billing.dto.BillingRequestDto;
import com.hospital.billing.enums.BillingStatus;
import com.hospital.billing.service.BillingService;

import lombok.RequiredArgsConstructor;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AppointmentKafkaConsumer {

	private final BillingService billingService;

	@KafkaListener(topics = "appointment-created", groupId = "billing-service-group", containerFactory = "appointmentKafkaListenerContainerFactory")
	public void consumeAppointmentCreated(AppointmentCreatedEvent event) {

		System.out.println("Received appointment event: " + event);

		BillingRequestDto request = new BillingRequestDto();

		request.setAppointmentId(event.getAppointmentId());

		request.setPatientId(event.getPatientId());

		request.setConsultationFee(new BigDecimal("500.00"));

		request.setMedicineFee(BigDecimal.ZERO);

		request.setTestFee(BigDecimal.ZERO);

		request.setOtherCharges(BigDecimal.ZERO);

		request.setBillingStatus(BillingStatus.GENERATED);

		billingService.createBilling(request);

		System.out.println("Billing created for appointment: " + event.getAppointmentId());
	}
}