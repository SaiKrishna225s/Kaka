package com.kaka.Kaka.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "organizations")
@Getter
@Setter
public class Organization extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String domain;

    private String logoUrl;

    private String timezone;

    private String currency;
}
