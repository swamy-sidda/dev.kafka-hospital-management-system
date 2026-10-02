package com.hospital.billing.config;

import com.hospital.billing.dto.AppointmentCreatedEvent;
import com.hospital.billing.dto.MedicinePrescribedEvent;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

	// Appointment Consumer
	@Bean
	public ConsumerFactory<String, AppointmentCreatedEvent> appointmentConsumerFactory() {

		Map<String, Object> config = new HashMap<>();

		config.put("bootstrap.servers", "localhost:9092");
		config.put("group.id", "billing-service-group");
		config.put("key.deserializer", StringDeserializer.class);
		config.put("value.deserializer", JsonDeserializer.class);
		config.put("spring.json.trusted.packages", "com.hospital.billing.dto");
		config.put("spring.json.value.default.type", "com.hospital.billing.dto.AppointmentCreatedEvent");
		config.put("spring.json.use.type.headers", false);

		return new DefaultKafkaConsumerFactory<>(config);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, AppointmentCreatedEvent> appointmentKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, AppointmentCreatedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(appointmentConsumerFactory());

		return factory;
	}

	// Medicine Consumer
	@Bean
	public ConsumerFactory<String, MedicinePrescribedEvent> medicineConsumerFactory() {

		Map<String, Object> config = new HashMap<>();

		config.put("bootstrap.servers", "localhost:9092");
		config.put("group.id", "billing-medicine-group");
		config.put("key.deserializer", StringDeserializer.class);
		config.put("value.deserializer", JsonDeserializer.class);
		config.put("spring.json.trusted.packages", "com.hospital.billing.dto");
		config.put("spring.json.value.default.type", "com.hospital.billing.dto.MedicinePrescribedEvent");
		config.put("spring.json.use.type.headers", false);

		return new DefaultKafkaConsumerFactory<>(config);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, MedicinePrescribedEvent> medicineKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, MedicinePrescribedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(medicineConsumerFactory());

		return factory;
	}
}