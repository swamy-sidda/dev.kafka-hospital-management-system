package com.hospital.appointment.kafka;

import com.hospital.appointment.dto.AppointmentCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AppointmentKafkaProducer {

	private static final String TOPIC = "appointment-created";

	private final KafkaTemplate<String, AppointmentCreatedEvent> kafkaTemplate;

	public AppointmentKafkaProducer(KafkaTemplate<String, AppointmentCreatedEvent> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendAppointmentCreatedEvent(AppointmentCreatedEvent event) {

		kafkaTemplate.send(TOPIC, String.valueOf(event.getAppointmentId()), event);
	}
}