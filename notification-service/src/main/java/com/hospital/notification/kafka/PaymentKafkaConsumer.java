package com.hospital.notification.kafka;

import com.hospital.notification.dto.PaymentCompletedEvent;
import com.hospital.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentKafkaConsumer {

	private final NotificationService notificationService;

	@KafkaListener(topics = "payment-completed", groupId = "notification-service-group", containerFactory = "paymentKafkaListenerContainerFactory")
	public void consumePaymentCompleted(PaymentCompletedEvent event) {

		System.out.println("=================================");
		System.out.println("PAYMENT COMPLETED");
		System.out.println("Payment ID : " + event.getPaymentId());
		System.out.println("Bill ID    : " + event.getBillId());
		System.out.println("Patient ID : " + event.getPatientId());
		System.out.println("Amount     : ₹" + event.getAmount());
		System.out.println("Method     : " + event.getPaymentMethod());
		System.out.println("Date       : " + event.getPaymentDate());
		System.out.println("=================================");

		notificationService.savePaymentNotification(event);
	}
}