package com.hospital.patient.service.impl;

import com.hospital.patient.dto.PatientRequestDto;
import com.hospital.patient.dto.PatientResponseDto;
import com.hospital.patient.entity.Patient;
import com.hospital.patient.repository.PatientRepository;
import com.hospital.patient.service.PatientService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.hospital.patient.exception.PatientNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public PatientResponseDto createPatient(PatientRequestDto request) {

        log.info("Creating patient: {} {}", request.getFirstName(), request.getLastName());

        Patient patient = new Patient();

        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setAge(request.getAge());
        patient.setGender(request.getGender());
        patient.setPhone(request.getPhone());
        patient.setEmail(request.getEmail());
        patient.setAddress(request.getAddress());
        patient.setBloodGroup(request.getBloodGroup());

        Patient savedPatient = patientRepository.save(patient);

        log.info("Patient created successfully with id: {}", savedPatient.getId());

        return mapToResponse(savedPatient);
    }

    @Override
    public PatientResponseDto getPatientById(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new PatientNotFoundException(
                                "Patient not found with id: " + id
                        ));

        return mapToResponse(patient);
    }

    @Override
    public List<PatientResponseDto> getAllPatients() {

        log.info("Fetching all patients");

        return patientRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public PatientResponseDto updatePatient(Long id, PatientRequestDto request) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new PatientNotFoundException(
                                "Patient not found with id: " + id
                        ));

        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setAge(request.getAge());
        patient.setGender(request.getGender());
        patient.setPhone(request.getPhone());
        patient.setEmail(request.getEmail());
        patient.setAddress(request.getAddress());
        patient.setBloodGroup(request.getBloodGroup());

        Patient updatedPatient = patientRepository.save(patient);

        return mapToResponse(updatedPatient);
    }
    @Override
    public void deletePatient(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new PatientNotFoundException(
                                "Patient not found with id: " + id
                        ));

        patientRepository.delete(patient);
    }

    private PatientResponseDto mapToResponse(Patient patient) {

        return new PatientResponseDto(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getAge(),
                patient.getGender(),
                patient.getPhone(),
                patient.getEmail(),
                patient.getAddress(),
                patient.getBloodGroup()
        );
    }
}