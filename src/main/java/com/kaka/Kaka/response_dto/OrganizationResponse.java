package com.kaka.Kaka.response_dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrganizationResponse {
    private UUID id;
    private String name;
    private String domain;
    private String logoUrl;
    private String timezone;
    private String currency;
    private LocalDateTime createdAt;
    
    // Super Admin details (if available/requested)
    private String superAdminEmail;
    private String superAdminName;
}
