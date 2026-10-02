package com.hospital.payment.client;

import com.hospital.payment.dto.AppointmentResponseDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "appointment-service")
public interface AppointmentClient {

    @GetMapping("/api/appointments/{id}")
    AppointmentResponseDto getAppointmentById(
            @PathVariable("id") Long id
    );
}