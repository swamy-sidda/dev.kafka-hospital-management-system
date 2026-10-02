package com.hospital.notification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long patientId;

	private Long appointmentId;

	private Long medicineId;

	private Long paymentId;

	private Long billId;

	private String message;

	private String notificationType;

	private LocalDateTime createdAt;
}