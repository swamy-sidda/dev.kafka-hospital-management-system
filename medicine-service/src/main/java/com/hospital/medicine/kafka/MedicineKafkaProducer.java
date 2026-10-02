package com.hospital.medicine.kafka;

import com.hospital.medicine.dto.MedicinePrescribedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MedicineKafkaProducer {

    private static final String TOPIC = "medicine-prescribed";

    private final KafkaTemplate<String, MedicinePrescribedEvent> kafkaTemplate;

    public MedicineKafkaProducer(
            KafkaTemplate<String, MedicinePrescribedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMedicinePrescribedEvent(
            MedicinePrescribedEvent event) {

        kafkaTemplate.send(
                TOPIC,
                String.valueOf(event.getBillId()),
                event
        );
    }
}