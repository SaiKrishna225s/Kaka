package com.kaka.Kaka.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "assets")
@Getter
@Setter
public class Asset extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_uuid")
    private Organization organization;

    private String assetName;

    private String assetCode;

    private String category;
}
