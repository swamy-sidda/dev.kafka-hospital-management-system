package com.hospital.notification.kafka;

import com.hospital.notification.dto.MedicinePrescribedEvent;
import com.hospital.notification.entity.Notification;
import com.hospital.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MedicineKafkaConsumer {

	private final NotificationRepository notificationRepository;

	@KafkaListener(topics = "medicine-prescribed", groupId = "notification-medicine-group", containerFactory = "medicineKafkaListenerContainerFactory")
	public void consumeMedicinePrescribed(MedicinePrescribedEvent event) {

		System.out.println("=================================");
		System.out.println("MEDICINE PRESCRIBED");
		System.out.println("Medicine ID : " + event.getMedicineId());
		System.out.println("Bill ID     : " + event.getBillId());
		System.out.println("Quantity    : " + event.getQuantity());
		System.out.println("Charge      : ₹" + event.getMedicineCharge());
		System.out.println("=================================");

		Notification notification = new Notification();

		notification.setMedicineId(event.getMedicineId());
		notification.setBillId(event.getBillId());

		notification.setMessage("Medicine prescribed successfully");

		notification.setNotificationType("MEDICINE_PRESCRIBED");

		notification.setCreatedAt(LocalDateTime.now());

		notificationRepository.save(notification);
	}
}