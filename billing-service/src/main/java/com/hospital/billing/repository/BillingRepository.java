package com.hospital.billing.repository;

import com.hospital.billing.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillingRepository extends JpaRepository<Billing, Long> {

    Optional<Billing> findByAppointmentId(Long appointmentId);
}