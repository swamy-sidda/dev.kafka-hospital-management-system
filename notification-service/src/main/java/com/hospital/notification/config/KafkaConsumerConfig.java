package com.hospital.notification.config;

import com.hospital.notification.dto.AppointmentCreatedEvent;
import com.hospital.notification.dto.MedicinePrescribedEvent;
import com.hospital.notification.dto.PaymentCompletedEvent;
import com.hospital.notification.dto.PaymentFailedEvent;

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

	// Payment Completed Consumer
	@Bean
	public ConsumerFactory<String, PaymentCompletedEvent> paymentConsumerFactory() {

		Map<String, Object> config = new HashMap<>();

		config.put("bootstrap.servers", "localhost:9092");
		config.put("group.id", "notification-service-group");
		config.put("key.deserializer", StringDeserializer.class);
		config.put("value.deserializer", JsonDeserializer.class);

		config.put("spring.json.trusted.packages", "com.hospital.notification.dto");

		config.put("spring.json.value.default.type", "com.hospital.notification.dto.PaymentCompletedEvent");

		config.put("spring.json.use.type.headers", false);

		return new DefaultKafkaConsumerFactory<>(config);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, PaymentCompletedEvent> paymentKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, PaymentCompletedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(paymentConsumerFactory());

		return factory;
	}

	// Appointment Created Consumer
	@Bean
	public ConsumerFactory<String, AppointmentCreatedEvent> appointmentConsumerFactory() {

		Map<String, Object> config = new HashMap<>();

		config.put("bootstrap.servers", "localhost:9092");
		config.put("group.id", "notification-appointment-group");
		config.put("key.deserializer", StringDeserializer.class);
		config.put("value.deserializer", JsonDeserializer.class);

		config.put("spring.json.trusted.packages", "com.hospital.notification.dto");

		config.put("spring.json.value.default.type", "com.hospital.notification.dto.AppointmentCreatedEvent");

		config.put("spring.json.use.type.headers", false);

		return new DefaultKafkaConsumerFactory<>(config);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, AppointmentCreatedEvent> appointmentKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, AppointmentCreatedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(appointmentConsumerFactory());

		return factory;
	}

	@Bean
	public ConsumerFactory<String, MedicinePrescribedEvent> medicineConsumerFactory() {

		Map<String, Object> config = new HashMap<>();

		config.put("bootstrap.servers", "localhost:9092");
		config.put("group.id", "notification-medicine-group");
		config.put("key.deserializer", StringDeserializer.class);
		config.put("value.deserializer", JsonDeserializer.class);

		config.put("spring.json.trusted.packages", "com.hospital.notification.dto");

		config.put("spring.json.value.default.type", "com.hospital.notification.dto.MedicinePrescribedEvent");

		config.put("spring.json.use.type.headers", false);

		return new DefaultKafkaConsumerFactory<>(config);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, MedicinePrescribedEvent> medicineKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, MedicinePrescribedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(medicineConsumerFactory());

		return factory;
	}
	
	@Bean
	public ConsumerFactory<String, PaymentFailedEvent>
	paymentFailedConsumerFactory() {

	    Map<String, Object> config = new HashMap<>();

	    config.put("bootstrap.servers", "localhost:9092");
	    config.put(
	            "group.id",
	            "notification-payment-failed-group"
	    );
	    config.put(
	            "key.deserializer",
	            StringDeserializer.class
	    );
	    config.put(
	            "value.deserializer",
	            JsonDeserializer.class
	    );

	    config.put(
	            "spring.json.trusted.packages",
	            "com.hospital.notification.dto"
	    );

	    config.put(
	            "spring.json.value.default.type",
	            "com.hospital.notification.dto.PaymentFailedEvent"
	    );

	    config.put(
	            "spring.json.use.type.headers",
	            false
	    );

	    return new DefaultKafkaConsumerFactory<>(config);
	}


	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, PaymentFailedEvent>
	paymentFailedKafkaListenerContainerFactory() {

	    ConcurrentKafkaListenerContainerFactory<String, PaymentFailedEvent>
	            factory =
	            new ConcurrentKafkaListenerContainerFactory<>();

	    factory.setConsumerFactory(
	            paymentFailedConsumerFactory()
	    );

	    return factory;
	}
}