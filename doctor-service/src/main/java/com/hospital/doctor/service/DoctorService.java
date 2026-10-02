package com.hospital.doctor.service;

import com.hospital.doctor.dto.DoctorRequestDto;
import com.hospital.doctor.dto.DoctorResponseDto;

import java.util.List;

public interface DoctorService {

    DoctorResponseDto createDoctor(DoctorRequestDto request);

    DoctorResponseDto getDoctorById(Long id);

    List<DoctorResponseDto> getAllDoctors();

    DoctorResponseDto updateDoctor(Long id, DoctorRequestDto request);

    void deleteDoctor(Long id);
}