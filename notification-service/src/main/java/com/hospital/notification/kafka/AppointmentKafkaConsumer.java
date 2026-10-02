package com.hospital.notification.kafka;

import com.hospital.notification.dto.AppointmentCreatedEvent;
import com.hospital.notification.entity.Notification;
import com.hospital.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AppointmentKafkaConsumer {

    private final NotificationRepository notificationRepository;

    @KafkaListener(
            topics = "appointment-created",
            groupId = "notification-appointment-group",
            containerFactory = "appointmentKafkaListenerContainerFactory"
    )
    public void consumeAppointmentCreated(
            AppointmentCreatedEvent event) {

        System.out.println("=================================");
        System.out.println("APPOINTMENT CREATED");
        System.out.println("Appointment ID : " + event.getAppointmentId());
        System.out.println("Patient ID     : " + event.getPatientId());
        System.out.println("Doctor ID      : " + event.getDoctorId());
        System.out.println("=================================");

        Notification notification = new Notification();

        notification.setPatientId(event.getPatientId());
        notification.setAppointmentId(event.getAppointmentId());

        notification.setMessage(
                "Appointment booked successfully"
        );

        notification.setNotificationType(
                "APPOINTMENT_CREATED"
        );

        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }
}