package com.hospital.payment.config;

import com.hospital.payment.dto.PaymentCompletedEvent;
import com.hospital.payment.dto.PaymentFailedEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

	// ==============================
	// Payment Completed Producer
	// ==============================

	@Bean
	public ProducerFactory<String, PaymentCompletedEvent> paymentCompletedProducerFactory() {

		Map<String, Object> config = new HashMap<>();

		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return new DefaultKafkaProducerFactory<>(config);
	}

	@Bean
	public KafkaTemplate<String, PaymentCompletedEvent> paymentCompletedKafkaTemplate() {

		return new KafkaTemplate<>(paymentCompletedProducerFactory());
	}

	// ==============================
	// Payment Failed Producer
	// ==============================

	@Bean
	public ProducerFactory<String, PaymentFailedEvent> paymentFailedProducerFactory() {

		Map<String, Object> config = new HashMap<>();

		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return new DefaultKafkaProducerFactory<>(config);
	}

	@Bean
	public KafkaTemplate<String, PaymentFailedEvent> paymentFailedKafkaTemplate() {

		return new KafkaTemplate<>(paymentFailedProducerFactory());
	}
}