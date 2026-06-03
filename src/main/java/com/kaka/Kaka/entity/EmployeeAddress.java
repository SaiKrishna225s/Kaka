package com.kaka.Kaka.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employee_addresses")
@Getter
@Setter
public class EmployeeAddress extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_uuid")
    private Employee employee;

    private String addressType;

    private String line1;

    private String line2;

    private String city;

    private String state;

    private String country;

    private String postalCode;
}
