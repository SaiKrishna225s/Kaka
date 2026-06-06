package com.kaka.Kaka.request_dto;

import lombok.Data;

@Data
public class UpdateOrganizationRequest {
    private String name;
    private String domain;
    private String logoUrl;
    private String timezone;
    private String currency;
}
