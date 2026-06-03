package com.kaka.Kaka.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "employee_salary")
@Getter
@Setter
public class EmployeeSalary extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_uuid")
    private Employee employee;

    private LocalDate effectiveFrom;

    private Double basic;

    private Double hra;

    private Double specialAllowance;

    private Double grossSalary;
}
