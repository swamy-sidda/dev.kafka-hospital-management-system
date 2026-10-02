package com.hospital.appointment.controller;

import com.hospital.appointment.dto.AppointmentRequestDto;
import com.hospital.appointment.dto.AppointmentResponseDto;
import com.hospital.appointment.service.AppointmentService;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    // CREATE
    @PostMapping
    public ResponseEntity<AppointmentResponseDto> createAppointment(
            @Valid @RequestBody AppointmentRequestDto request) {

        AppointmentResponseDto response =
                appointmentService.createAppointment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDto> getAppointmentById(
            @PathVariable Long id) {

        AppointmentResponseDto response =
                appointmentService.getAppointmentById(id);

        return ResponseEntity.ok(response);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointments() {

        List<AppointmentResponseDto> response =
                appointmentService.getAllAppointments();

        return ResponseEntity.ok(response);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponseDto> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentRequestDto request) {

        AppointmentResponseDto response =
                appointmentService.updateAppointment(id, request);

        return ResponseEntity.ok(response);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(
            @PathVariable Long id) {

        appointmentService.deleteAppointment(id);

        return ResponseEntity.noContent().build();
    }
}