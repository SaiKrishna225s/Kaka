package com.kaka.Kaka.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "designations")
@Getter
@Setter
public class Designation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_uuid")
    private Organization organization;

    @Column(nullable = false)
    private String title;

    private String level;
}
