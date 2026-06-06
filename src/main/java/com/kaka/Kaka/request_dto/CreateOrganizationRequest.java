package com.kaka.Kaka.request_dto;

import lombok.Data;

@Data
public class CreateOrganizationRequest {
    private String organizationName;
    private String domain;
    private String superAdminEmail;
    private String superAdminFirstName;
    private String superAdminLastName;
}
