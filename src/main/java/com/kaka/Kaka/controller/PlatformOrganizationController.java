package com.kaka.Kaka.controller;

import com.kaka.Kaka.request_dto.CreateOrganizationRequest;
import com.kaka.Kaka.request_dto.UpdateOrganizationRequest;
import com.kaka.Kaka.response_dto.OrganizationResponse;
import com.kaka.Kaka.service.PlatformOrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/platform/organizations")
@PreAuthorize("hasRole('PLATFORM_ADMIN')")
public class PlatformOrganizationController {

    private final PlatformOrganizationService platformOrganizationService;

    public PlatformOrganizationController(PlatformOrganizationService platformOrganizationService) {
        this.platformOrganizationService = platformOrganizationService;
    }

    @PostMapping
    public ResponseEntity<OrganizationResponse> createOrganization(@RequestBody CreateOrganizationRequest request) {
        OrganizationResponse response = platformOrganizationService.createOrganization(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrganizationResponse>> getAllOrganizations() {
        List<OrganizationResponse> response = platformOrganizationService.getAllOrganizations();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationResponse> getOrganizationById(@PathVariable UUID id) {
        OrganizationResponse response = platformOrganizationService.getOrganizationById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganizationResponse> updateOrganization(
            @PathVariable UUID id, 
            @RequestBody UpdateOrganizationRequest request) {
        OrganizationResponse response = platformOrganizationService.updateOrganization(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrganizationResponse> patchOrganization(
            @PathVariable UUID id, 
            @RequestBody UpdateOrganizationRequest request) {
        OrganizationResponse response = platformOrganizationService.patchOrganization(id, request);
        return ResponseEntity.ok(response);
    }
}
