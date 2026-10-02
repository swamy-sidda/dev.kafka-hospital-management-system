package com.hospital.doctor.dto;

import com.hospital.doctor.entity.AvailabilityStatus;
import com.hospital.doctor.entity.Gender;
import com.hospital.doctor.entity.Specialization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRequestDto {

    private String firstName;

    private String lastName;

    private Gender gender;

    private Specialization specialization;

    private String qualification;

    private String phone;

    private String email;

    private Integer experience;

    private AvailabilityStatus availabilityStatus;
}