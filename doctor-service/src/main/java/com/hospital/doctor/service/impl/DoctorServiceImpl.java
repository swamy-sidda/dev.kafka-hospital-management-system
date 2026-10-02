package com.hospital.doctor.service.impl;

import com.hospital.doctor.dto.DoctorRequestDto;
import com.hospital.doctor.dto.DoctorResponseDto;
import com.hospital.doctor.entity.Doctor;
import com.hospital.doctor.exception.DoctorNotFoundException;
import com.hospital.doctor.exception.DuplicateDoctorException;
import com.hospital.doctor.repository.DoctorRepository;
import com.hospital.doctor.service.DoctorService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Override
    public DoctorResponseDto createDoctor(DoctorRequestDto request) {

        if (doctorRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateDoctorException(
                    "Doctor with email already exists: " + request.getEmail()
            );
        }

        if (doctorRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateDoctorException(
                    "Doctor with phone already exists: " + request.getPhone()
            );
        }

        Doctor doctor = new Doctor();

        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setGender(request.getGender());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setQualification(request.getQualification());
        doctor.setPhone(request.getPhone());
        doctor.setEmail(request.getEmail());
        doctor.setExperience(request.getExperience());
        doctor.setAvailabilityStatus(request.getAvailabilityStatus());

        Doctor savedDoctor = doctorRepository.save(doctor);

        return mapToResponse(savedDoctor);
    }
    @Override
    public DoctorResponseDto getDoctorById(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new DoctorNotFoundException(
                                "Doctor not found with id: " + id));

        return mapToResponse(doctor);
    }

    @Override
    public List<DoctorResponseDto> getAllDoctors() {

        return doctorRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    
@Override
    public DoctorResponseDto updateDoctor(
            Long id,
            DoctorRequestDto request) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new DoctorNotFoundException(
                                "Doctor not found with id: " + id));

        if (doctorRepository.existsByEmailAndIdNot(
                request.getEmail(), id)) {

            throw new DuplicateDoctorException(
                    "Doctor with email already exists: "
                            + request.getEmail()
            );
        }

        if (doctorRepository.existsByPhoneAndIdNot(
                request.getPhone(), id)) {

            throw new DuplicateDoctorException(
                    "Doctor with phone already exists: "
                            + request.getPhone()
            );
        }
        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setGender(request.getGender());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setQualification(request.getQualification());
        doctor.setPhone(request.getPhone());
        doctor.setEmail(request.getEmail());
        doctor.setExperience(request.getExperience());
        doctor.setAvailabilityStatus(request.getAvailabilityStatus());

        Doctor updatedDoctor = doctorRepository.save(doctor);

        return mapToResponse(updatedDoctor);
    }
    @Override
    public void deleteDoctor(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new DoctorNotFoundException(
                                "Doctor not found with id: " + id));

        doctorRepository.delete(doctor);
    }

    private DoctorResponseDto mapToResponse(Doctor doctor) {

        return new DoctorResponseDto(
                doctor.getId(),
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getGender(),
                doctor.getSpecialization(),
                doctor.getQualification(),
                doctor.getPhone(),
                doctor.getEmail(),
                doctor.getExperience(),
                doctor.getAvailabilityStatus()
        );
    }
}