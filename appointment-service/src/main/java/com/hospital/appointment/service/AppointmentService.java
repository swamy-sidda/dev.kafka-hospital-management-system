package com.hospital.appointment.service;

import com.hospital.appointment.dto.AppointmentRequestDto;
import com.hospital.appointment.dto.AppointmentResponseDto;

import java.util.List;

public interface AppointmentService {

    AppointmentResponseDto createAppointment(AppointmentRequestDto request);

    AppointmentResponseDto getAppointmentById(Long id);

    List<AppointmentResponseDto> getAllAppointments();

    AppointmentResponseDto updateAppointment(Long id, AppointmentRequestDto request);

    void deleteAppointment(Long id);
}