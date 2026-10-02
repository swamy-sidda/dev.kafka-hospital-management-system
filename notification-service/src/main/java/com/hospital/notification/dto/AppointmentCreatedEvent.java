package com.hospital.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentCreatedEvent {

    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
}