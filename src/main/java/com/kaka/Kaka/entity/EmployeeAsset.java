package com.kaka.Kaka.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "employee_assets")
@Getter
@Setter
public class EmployeeAsset extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_uuid")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_uuid")
    private Asset asset;

    private LocalDate assignedDate;

    private LocalDate returnDate;
}
