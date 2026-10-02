package com.hospital.patient.service;

import com.hospital.patient.dto.PatientRequestDto;
import com.hospital.patient.dto.PatientResponseDto;

import java.util.List;

public interface PatientService {

    PatientResponseDto createPatient(PatientRequestDto request);

    PatientResponseDto getPatientById(Long id);

    List<PatientResponseDto> getAllPatients();

    PatientResponseDto updatePatient(Long id, PatientRequestDto request);

    void deletePatient(Long id);
}