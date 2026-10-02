package com.hospital.notification.kafka;

import com.hospital.notification.dto.PaymentFailedEvent;
import com.hospital.notification.entity.Notification;
import com.hospital.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentFailedKafkaConsumer {

	private final NotificationRepository notificationRepository;

	@KafkaListener(topics = "payment-failed", groupId = "notification-payment-failed-group", containerFactory = "paymentFailedKafkaListenerContainerFactory")
	public void consumePaymentFailed(PaymentFailedEvent event) {

		System.out.println("=================================");
		System.out.println("PAYMENT FAILED");
		System.out.println("Payment ID : " + event.getPaymentId());
		System.out.println("Bill ID    : " + event.getBillId());
		System.out.println("Patient ID : " + event.getPatientId());
		System.out.println("Amount     : ₹" + event.getAmount());
		System.out.println("Method     : " + event.getPaymentMethod());
		System.out.println("=================================");

		Notification notification = new Notification();

		notification.setPatientId(event.getPatientId());
		notification.setPaymentId(event.getPaymentId());
		notification.setBillId(event.getBillId());

		notification.setMessage("Payment of ₹" + event.getAmount() + " failed");

		notification.setNotificationType("PAYMENT_FAILED");

		notification.setCreatedAt(LocalDateTime.now());

		notificationRepository.save(notification);
	}
}