package com.hospital.patient.dto;

import com.hospital.patient.enums.BloodGroup;
import com.hospital.patient.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDto {

    private Long id;

    private String firstName;

    private String lastName;

    private Integer age;

    private Gender gender;

    private String phone;

    private String email;

    private String address;

    private BloodGroup bloodGroup;
}