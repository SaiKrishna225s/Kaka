package com.kaka.Kaka.entity;

import com.kaka.Kaka.entity.enums.EmployeeStatus;
import com.kaka.Kaka.entity.enums.EmploymentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Getter
@Setter
public class Employee extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_uuid")
    private Organization organization;

    @Column(unique = true)
    private String employeeCode;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    private String phone;

    private LocalDate dateOfBirth;

    private String gender;

    private LocalDate joiningDate;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_uuid")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "designation_uuid")
    private Designation designation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_uuid")
    private Employee manager;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    private String profileImage;
}
