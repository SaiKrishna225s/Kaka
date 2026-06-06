package com.kaka.Kaka.service;

import com.kaka.Kaka.request_dto.CreateOrganizationRequest;
import com.kaka.Kaka.request_dto.UpdateOrganizationRequest;
import com.kaka.Kaka.response_dto.OrganizationResponse;

import java.util.List;
import java.util.UUID;

public interface PlatformOrganizationService {
    OrganizationResponse createOrganization(CreateOrganizationRequest request);
    List<OrganizationResponse> getAllOrganizations();
    OrganizationResponse getOrganizationById(UUID id);
    OrganizationResponse updateOrganization(UUID id, UpdateOrganizationRequest request);
    OrganizationResponse patchOrganization(UUID id, UpdateOrganizationRequest request);
}
