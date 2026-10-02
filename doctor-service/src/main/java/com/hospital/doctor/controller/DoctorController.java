package com.hospital.doctor.controller;

import com.hospital.doctor.dto.DoctorRequestDto;
import com.hospital.doctor.dto.DoctorResponseDto;
import com.hospital.doctor.service.DoctorService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    // CREATE DOCTOR
    @PostMapping
    public ResponseEntity<DoctorResponseDto> createDoctor(
            @RequestBody DoctorRequestDto request) {

        DoctorResponseDto response =
                doctorService.createDoctor(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // GET DOCTOR BY ID
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> getDoctorById(
            @PathVariable Long id) {

        DoctorResponseDto response =
                doctorService.getDoctorById(id);

        return ResponseEntity.ok(response);
    }

    // GET ALL DOCTORS
    @GetMapping
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctors() {

        List<DoctorResponseDto> response =
                doctorService.getAllDoctors();

        return ResponseEntity.ok(response);
    }

    // UPDATE DOCTOR
    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDto> updateDoctor(
            @PathVariable Long id,
            @RequestBody DoctorRequestDto request) {

        DoctorResponseDto response =
                doctorService.updateDoctor(id, request);

        return ResponseEntity.ok(response);
    }

    // DELETE DOCTOR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(
            @PathVariable Long id) {

        doctorService.deleteDoctor(id);

        return ResponseEntity.noContent().build();
    }
}