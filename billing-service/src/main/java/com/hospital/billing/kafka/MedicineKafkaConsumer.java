package com.hospital.billing.kafka;

import com.hospital.billing.dto.MedicineChargeRequestDto;
import com.hospital.billing.dto.MedicinePrescribedEvent;
import com.hospital.billing.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicineKafkaConsumer {

	private final BillingService billingService;

	@KafkaListener(topics = "medicine-prescribed", groupId = "billing-medicine-group", containerFactory = "medicineKafkaListenerContainerFactory")
	public void consumeMedicinePrescribed(MedicinePrescribedEvent event) {

		System.out.println("Received medicine event: " + event);

		MedicineChargeRequestDto request = new MedicineChargeRequestDto();

		request.setMedicineFee(event.getMedicineCharge());

		billingService.updateMedicineFee(event.getBillId(), request);

		System.out.println("Medicine charge added to bill: " + event.getBillId());
	}
}