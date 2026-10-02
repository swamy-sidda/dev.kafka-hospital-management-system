package com.hospital.payment.kafka;

import com.hospital.payment.dto.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentKafkaProducer {

	private static final String TOPIC = "payment-completed";

	private final KafkaTemplate<String, PaymentCompletedEvent> kafkaTemplate;

	public PaymentKafkaProducer(
			@Qualifier("paymentCompletedKafkaTemplate") KafkaTemplate<String, PaymentCompletedEvent> kafkaTemplate) {

		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendPaymentCompletedEvent(PaymentCompletedEvent event) {

		kafkaTemplate.send(TOPIC, String.valueOf(event.getPaymentId()), event);
	}
}