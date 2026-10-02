package com.hospital.patient.entity;

import com.hospital.patient.enums.BloodGroup;
import com.hospital.patient.enums.Gender;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phone;

    private String email;

    private String address;

    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;
}