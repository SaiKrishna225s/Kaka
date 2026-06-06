package com.kaka.Kaka.service.impl;

import com.kaka.Kaka.entity.Employee;
import com.kaka.Kaka.entity.Organization;
import com.kaka.Kaka.entity.Role;
import com.kaka.Kaka.entity.UserRole;
import com.kaka.Kaka.entity.enums.EmployeeStatus;
import com.kaka.Kaka.entity.enums.EmploymentType;
import com.kaka.Kaka.repository.EmployeeRepository;
import com.kaka.Kaka.repository.OrganizationRepository;
import com.kaka.Kaka.repository.RoleRepository;
import com.kaka.Kaka.repository.UserRoleRepository;
import com.kaka.Kaka.request_dto.CreateOrganizationRequest;
import com.kaka.Kaka.request_dto.UpdateOrganizationRequest;
import com.kaka.Kaka.response_dto.OrganizationResponse;
import com.kaka.Kaka.service.PlatformOrganizationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PlatformOrganizationServiceImpl implements PlatformOrganizationService {

    private final OrganizationRepository organizationRepository;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public PlatformOrganizationServiceImpl(OrganizationRepository organizationRepository,
                                           EmployeeRepository employeeRepository,
                                           RoleRepository roleRepository,
                                           UserRoleRepository userRoleRepository) {
        this.organizationRepository = organizationRepository;
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    @Transactional
    public OrganizationResponse createOrganization(CreateOrganizationRequest request) {
        // 1. Create Organization
        Organization org = new Organization();
        org.setName(request.getOrganizationName());
        org.setDomain(request.getDomain());
        org = organizationRepository.save(org);

        // 2. Create Super Admin Employee
        Employee superAdmin = new Employee();
        superAdmin.setOrganization(org);
        superAdmin.setFirstName(request.getSuperAdminFirstName());
        superAdmin.setLastName(request.getSuperAdminLastName());
        superAdmin.setEmail(request.getSuperAdminEmail());
        
        // TODO: Use PasswordEncoder once Spring Security is integrated
        superAdmin.setPassword("OrgAdmin@123"); 
        superAdmin.setStatus(EmployeeStatus.ACTIVE);
        superAdmin.setEmploymentType(EmploymentType.FULL_TIME);
        superAdmin = employeeRepository.save(superAdmin);

        // 3. Assign ORG_SUPER_ADMIN role
        Optional<Role> superAdminRoleOpt = roleRepository.findByName("ORG_SUPER_ADMIN");
        if (superAdminRoleOpt.isPresent()) {
            UserRole userRole = new UserRole();
            userRole.setEmployee(superAdmin);
            userRole.setRole(superAdminRoleOpt.get());
            userRoleRepository.save(userRole);
        } else {
            throw new RuntimeException("Role ORG_SUPER_ADMIN not found. Did the seeder run?");
        }

        return mapToResponse(org, superAdmin);
    }

    @Override
    public List<OrganizationResponse> getAllOrganizations() {
        return organizationRepository.findAll().stream()
                .map(org -> mapToResponse(org, null)) // We could fetch super admins, but keeping list light
                .collect(Collectors.toList());
    }

    @Override
    public OrganizationResponse getOrganizationById(UUID id) {
        Organization org = organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));
        return mapToResponse(org, null);
    }

    @Override
    @Transactional
    public OrganizationResponse updateOrganization(UUID id, UpdateOrganizationRequest request) {
        Organization org = organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        org.setName(request.getName());
        org.setDomain(request.getDomain());
        org.setLogoUrl(request.getLogoUrl());
        org.setTimezone(request.getTimezone());
        org.setCurrency(request.getCurrency());

        org = organizationRepository.save(org);
        return mapToResponse(org, null);
    }

    @Override
    @Transactional
    public OrganizationResponse patchOrganization(UUID id, UpdateOrganizationRequest request) {
        Organization org = organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        if (request.getName() != null) org.setName(request.getName());
        if (request.getDomain() != null) org.setDomain(request.getDomain());
        if (request.getLogoUrl() != null) org.setLogoUrl(request.getLogoUrl());
        if (request.getTimezone() != null) org.setTimezone(request.getTimezone());
        if (request.getCurrency() != null) org.setCurrency(request.getCurrency());

        org = organizationRepository.save(org);
        return mapToResponse(org, null);
    }

    private OrganizationResponse mapToResponse(Organization org, Employee superAdmin) {
        OrganizationResponse response = new OrganizationResponse();
        response.setId(org.getUuid());
        response.setName(org.getName());
        response.setDomain(org.getDomain());
        response.setLogoUrl(org.getLogoUrl());
        response.setTimezone(org.getTimezone());
        response.setCurrency(org.getCurrency());
        response.setCreatedAt(org.getCreatedAt());

        if (superAdmin != null) {
            response.setSuperAdminEmail(superAdmin.getEmail());
            response.setSuperAdminName(superAdmin.getFirstName() + " " + superAdmin.getLastName());
        }

        return response;
    }
}
