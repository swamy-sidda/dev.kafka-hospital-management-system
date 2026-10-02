package com.hospital.appointment.service.impl;

import com.hospital.appointment.client.BillingClient;

import com.hospital.appointment.dto.AppointmentRequestDto;
import com.hospital.appointment.dto.AppointmentResponseDto;
import com.hospital.appointment.entity.Appointment;
import com.hospital.appointment.enums.AppointmentStatus;
import com.hospital.appointment.exception.AppointmentNotFoundException;
import com.hospital.appointment.kafka.AppointmentKafkaProducer;
import com.hospital.appointment.repository.AppointmentRepository;
import com.hospital.appointment.service.AppointmentService;

import com.hospital.appointment.client.BillingClient;
import com.hospital.appointment.dto.BillingRequestDto;
import com.hospital.appointment.enums.BillingStatus;

import com.hospital.appointment.dto.AppointmentCreatedEvent;
import com.hospital.appointment.kafka.AppointmentKafkaProducer;

import java.math.BigDecimal;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

	private final AppointmentRepository appointmentRepository;
	private final BillingClient billingClient;
	private final AppointmentKafkaProducer appointmentKafkaProducer;

	@Override
	public AppointmentResponseDto createAppointment(AppointmentRequestDto request) {

		Appointment appointment = new Appointment();

		appointment.setPatientId(request.getPatientId());
		appointment.setDoctorId(request.getDoctorId());
		appointment.setAppointmentDate(request.getAppointmentDate());
		appointment.setAppointmentTime(request.getAppointmentTime());
		appointment.setReason(request.getReason());
		appointment.setStatus(request.getStatus());

		Appointment savedAppointment = appointmentRepository.save(appointment);

		AppointmentCreatedEvent event = new AppointmentCreatedEvent(savedAppointment.getId(),
				savedAppointment.getPatientId(), savedAppointment.getDoctorId());

		appointmentKafkaProducer.sendAppointmentCreatedEvent(event);

		return mapToResponse(savedAppointment);
	}

	@Override
	public AppointmentResponseDto getAppointmentById(Long id) {

		Appointment appointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with id: " + id));

		return mapToResponse(appointment);
	}

	@Override
	public List<AppointmentResponseDto> getAllAppointments() {

		return appointmentRepository.findAll().stream().map(this::mapToResponse).toList();
	}

	@Override
	public AppointmentResponseDto updateAppointment(Long id, AppointmentRequestDto request) {

		Appointment appointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with id: " + id));

		appointment.setPatientId(request.getPatientId());
		appointment.setDoctorId(request.getDoctorId());
		appointment.setAppointmentDate(request.getAppointmentDate());
		appointment.setAppointmentTime(request.getAppointmentTime());
		appointment.setReason(request.getReason());
		appointment.setStatus(request.getStatus());

		Appointment updatedAppointment = appointmentRepository.save(appointment);

		return mapToResponse(updatedAppointment);
	}

	@Override
	public void deleteAppointment(Long id) {

		Appointment appointment = appointmentRepository.findById(id)
				.orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with id: " + id));

		appointmentRepository.delete(appointment);
	}

	private AppointmentResponseDto mapToResponse(Appointment appointment) {

		return new AppointmentResponseDto(

				appointment.getId(),

				appointment.getPatientId(),

				appointment.getDoctorId(),

				appointment.getAppointmentDate(),

				appointment.getAppointmentTime(),

				appointment.getReason(),

				appointment.getStatus());
	}
}
