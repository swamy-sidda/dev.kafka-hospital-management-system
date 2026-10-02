package com.hospital.medicine.repository;

import com.hospital.medicine.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

}