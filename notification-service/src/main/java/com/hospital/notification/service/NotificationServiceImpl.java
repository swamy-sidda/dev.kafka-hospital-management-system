package com.hospital.notification.service;

import com.hospital.notification.dto.PaymentCompletedEvent;
import com.hospital.notification.entity.Notification;
import com.hospital.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

	private final NotificationRepository notificationRepository;

	@Override
	public void savePaymentNotification(PaymentCompletedEvent event) {

		Notification notification = new Notification();

		notification.setPatientId(event.getPatientId());
		notification.setPaymentId(event.getPaymentId());
		notification.setBillId(event.getBillId());

		notification.setMessage("Payment of ₹" + event.getAmount() + " completed successfully");

		notification.setNotificationType("PAYMENT_COMPLETED");

		notification.setCreatedAt(LocalDateTime.now());

		notificationRepository.save(notification);
	}
}