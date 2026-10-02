package com.hospital.payment.kafka;

import com.hospital.payment.dto.PaymentFailedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentFailedKafkaProducer {

	private static final String TOPIC = "payment-failed";

	private final KafkaTemplate<String, PaymentFailedEvent> kafkaTemplate;

	public PaymentFailedKafkaProducer(
			@Qualifier("paymentFailedKafkaTemplate") KafkaTemplate<String, PaymentFailedEvent> kafkaTemplate) {

		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendPaymentFailedEvent(PaymentFailedEvent event) {

		kafkaTemplate.send(TOPIC, String.valueOf(event.getPaymentId()), event);
	}
}