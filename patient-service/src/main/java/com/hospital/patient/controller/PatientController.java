
package com.hospital.patient.controller;

import com.hospital.patient.dto.PatientRequestDto;
import com.hospital.patient.dto.PatientResponseDto;
import com.hospital.patient.service.PatientService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    // CREATE PATIENT
    @PostMapping
    public ResponseEntity<PatientResponseDto> createPatient(
            @Valid @RequestBody PatientRequestDto request) {

        PatientResponseDto response =
                patientService.createPatient(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // GET PATIENT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDto> getPatientById(
            @PathVariable Long id) {

        PatientResponseDto response =
                patientService.getPatientById(id);

        return ResponseEntity.ok(response);
    }

    // GET ALL PATIENTS
    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getAllPatients() {

        List<PatientResponseDto> response =
                patientService.getAllPatients();

        return ResponseEntity.ok(response);
    }

    // UPDATE PATIENT
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequestDto request) {

        PatientResponseDto response =
                patientService.updatePatient(id, request);

        return ResponseEntity.ok(response);
    }

    // DELETE PATIENT
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(
            @PathVariable Long id) {

        patientService.deletePatient(id);

        return ResponseEntity.noContent().build();
    }
}
